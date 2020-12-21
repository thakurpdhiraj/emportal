<template>
  <v-container>
    <v-row>
      <v-col md="6" cols="12" offset-md="3">
        <v-form
          ref="empform"
          v-model="valid"
          @submit.prevent="submit"
          lazy-validation
        >
          <v-container>
            <v-row>
              <v-col cols="12" md="6">
                <v-text-field
                  v-model="name"
                  :counter="15"
                  :rules="[rules.required, rules.counter]"
                  label="Name"
                  required
                  clearable
                >
                </v-text-field>
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field
                  v-model="email"
                  :rules="[rules.required, rules.email]"
                  label="Email"
                  required
                  clearable
                >
                </v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12" md="6">
                <v-select
                  v-model="department"
                  :items="departmentList"
                  item-text="name"
                  item-value="id"
                  label="Department"
                  required
                ></v-select>
              </v-col>
              <v-col cols="12" md="6">
                <v-menu
                  v-model="showDate"
                  :close-on-content-click="false"
                  :nudge-right="40"
                  transition="scale-transition"
                  offset-y
                  min-width="290px"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="joining"
                      label="Joining Date"
                      append-icon="mdi-calendar"
                      readonly
                      v-bind="attrs"
                      v-on="on"
                      :rules="[rules.required]"
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    v-model="joining"
                    @input="showDate = false"
                    :max="maxDate"
                  ></v-date-picker>
                </v-menu>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12">
                <p class="caption">
                  All Fields are required.
                  <span class="red--text">*</span>
                </p>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="6">
                <v-btn
                  class="success mr-5 elevation-6"
                  :disabled="!valid"
                  @click="submit"
                >
                  Add
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
  </v-container>
</template>

<script>
export default {
  name: "Add",
  computed: {
    maxDate() {
      return new Date().toISOString().slice(0, 10);
    },
  },
  data: () => ({
    valid: true,
    showDate: false,
    name: null,
    email: null,
    department: null,
    departmentList: [
      { id: 1, name: "IT" },
      { id: 2, name: "BPS" },
    ],
    joining: null,
    rules: {
      required: (value) => !!value || "Required.",
      counter: (value) =>
        (value && value.length <= 15) || "Name should be max 15 characters.",
      email: (value) => {
        const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return pattern.test(value) || "Invalid Email.";
      },
    },
  }),
  methods: {
    submit() {
      this.$refs.empform.validate();
    },
    reset() {
      this.$refs.empform.reset();
    },
  },
};
</script>
