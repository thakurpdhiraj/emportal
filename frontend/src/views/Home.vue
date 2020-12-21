<template>
  <v-container class="pa-0">
    <v-row v-if="error">
      <v-col cols="12">
        <v-snackbar v-model="error" multi-line timeout="5000">
          {{ errorMessage }}
          <template v-slot:action="{ attrs }">
            <v-btn color="red" text v-bind="attrs" @click="error = false">
              Close
            </v-btn>
          </template>
        </v-snackbar>
      </v-col>
    </v-row>
    <v-row v-show="formVisible">
      <v-col md="6" cols="12" offset-md="3">
        <v-form @submit.prevent="submit">
          <v-container>
            <v-row>
              <v-col cols="12" md="6">
                <v-text-field v-model="name" label="Name" clearable>
                </v-text-field>
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field v-model="email" label="Email" clearable>
                </v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12" md="12">
                <v-select
                  v-model="department"
                  :items="departmentList"
                  label="Department"
                ></v-select>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12" md="6">
                <v-menu
                  v-model="showFromDate"
                  :close-on-content-click="false"
                  :nudge-right="40"
                  transition="scale-transition"
                  offset-y
                  min-width="290px"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="fromDate"
                      label="From Date"
                      append-icon="mdi-calendar"
                      readonly
                      v-bind="attrs"
                      v-on="on"
                      clearable
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    v-model="fromDate"
                    @input="showFromDate = false"
                    :max="maxDate"
                    :show-current="false"
                    no-title
                  ></v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="12" md="6">
                <v-menu
                  v-model="showToDate"
                  :close-on-content-click="false"
                  :nudge-right="40"
                  transition="scale-transition"
                  offset-y
                  min-width="290px"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="toDate"
                      label="To Date"
                      append-icon="mdi-calendar"
                      readonly
                      v-bind="attrs"
                      v-on="on"
                      clearable
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    v-model="toDate"
                    @input="showToDate = false"
                    :max="maxDate"
                    :show-current="false"
                    no-title
                  ></v-date-picker>
                </v-menu>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="6">
                <v-btn
                  class="success mr-5 elevation-6"
                  @click="submit"
                  :disabled="!valid"
                >
                  Submit
                </v-btn>
              </v-col>
              <v-col cols="6">
                <v-btn @click="reset" class="elevation-6">
                  Clear
                </v-btn>
              </v-col>
            </v-row>
          </v-container>
        </v-form>
      </v-col>
    </v-row>
    <v-row v-show="!formVisible" class="pa-0" style="top:200px;">
      <v-col md="12" cols="12">
        <v-btn color="primary" @click="showForm">
          Refine Search
        </v-btn>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-data-table
          :headers="headers"
          :items="result"
          :page.sync="pagination.page"
          :items-per-page="pagination.size"
          hide-default-footer
          disable-pagination
          class="elevation-15"
          :loading="loading"
          fixed-header
          height="65vh"
          loading-text="Loading... Please wait"
          disable-sort
        >
          <template v-slot:footer>
            <v-row class="py-2 mx-5" align="center" justify="center">
              <span class="grey--text">Items per page</span>
              <v-menu offset-y>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn
                    dark
                    text
                    color="primary"
                    class="ml-2"
                    v-bind="attrs"
                    v-on="on"
                  >
                    {{ pagination.size }}
                    <v-icon>mdi-chevron-down</v-icon>
                  </v-btn>
                </template>
                <v-list>
                  <v-list-item
                    v-for="(n, index) in sizeArray"
                    :key="index"
                    @click="changeSize(n)"
                    :disabled="!valid"
                  >
                    <v-list-item-title>{{ n }}</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>

              <v-spacer></v-spacer>
              <span class="mr-4 grey--text">
                Page {{ pagination.page + 1 }}
              </span>
              <v-btn
                :disabled="previousPageKey == null"
                fab
                small
                color="blue darken-3"
                class="mr-1"
                @click="previousPage"
              >
                <v-icon>mdi-chevron-left</v-icon>
              </v-btn>
              <v-btn
                :disabled="nextPageKey == null"
                fab
                small
                color="blue darken-3"
                class="ml-1"
                @click="nextPage"
              >
                <v-icon>mdi-chevron-right</v-icon>
              </v-btn>
            </v-row>
          </template>
        </v-data-table>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";
import backend from "../service/backend";
import every from "lodash/every";
import values from "lodash/values";
import isNull from "lodash/isNull";

export default {
  name: "Home",
  computed: {
    maxDate() {
      return new Date().toISOString().slice(0, 10);
    },
    empRequest() {
      return {
        name: this.name,
        email: this.email,
        toDate: this.toDate,
        fromDate: this.fromDate,
        department: this.department,
        pageKey: this.pagination.page,
        size: this.pagination.size,
      };
    },
  },
  data: () => ({
    formVisible: true,
    showFromDate: false,
    showToDate: false,
    valid: false,
    error: false,
    errorMessage: "",
    name: null,
    email: null,
    department: null,
    departmentList: ["IT", "BPS"],
    fromDate: null,
    toDate: null,
    result: [],
    nextPageKey: null,
    previousPageKey: null,
    loading: false,
    headers: [
      { text: "Name", value: "name", class: "grey lighten-3" },
      { text: "Email", value: "email", class: "grey lighten-3" },
      { text: "Department", value: "department", class: "grey lighten-3" },
      { text: "Joining", value: "joining", class: "grey lighten-3" },
    ],
    sizeArray: [10, 20, 50],
    pagination: {
      size: 10,
      page: 0,
    },
  }),
  methods: {
    submit() {
      this.formVisible = false;
      this.pagination.page = 0;
      this.fetchData();
    },
    fetchData() {
      this.loading = true;
      this.result = [];
      backend
        .getEmployees(this.empRequest)
        .then((response) => {
          this.result = response.data.employees;
          this.totalItems = this.result.length;
          this.nextPageKey = response.data.nextKey;
          this.previousPageKey = response.data.previousKey;
          this.loading = false;
        })
        .catch((err) => {
          this.error = true;
          this.errorMessage = err;
          this.loading = false;
        });
    },
    reset() {
      this.name = null;
      this.email = null;
      this.toDate = null;
      this.fromDate = null;
      this.department = null;
    },
    showForm() {
      this.formVisible = true;
    },
    nextPage() {
      this.pagination.page = this.nextPageKey;
      this.fetchData();
    },
    previousPage() {
      this.pagination.page = this.previousPageKey;
      this.fetchData();
    },
    changeSize(n) {
      this.pagination.size = n;
      this.pagination.page = 0;
      this.fetchData();
    },
  },
  watch: {
    empRequest: {
      handler() {
        let validation = [
          this.name,
          this.email,
          this.department,
          this.fromDate,
          this.toDate,
        ];
        this.valid = !values(validation).every(isNull);
      },
    },
  },
};
</script>
