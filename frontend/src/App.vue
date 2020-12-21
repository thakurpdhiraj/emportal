<template>
  <v-app>
    <v-app-bar app fixed>
      <v-spacer />
      <v-toolbar-title>{{ appName }}</v-toolbar-title>
      <v-spacer />
    </v-app-bar>
    <v-navigation-drawer permanent expand-on-hover app>
      <v-list nav>
        <v-list-item link>
          <v-list-item-content>
            <v-list-item-title class="title">
              E
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-divider></v-divider>
        <v-list-item-group color="primary" mandatory>
          <v-list-item
            v-for="item in items"
            :key="item.title"
            router
            :to="item.route"
            class="mt-10"
          >
            <v-list-item-icon>
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title class="title">
                {{ item.title }}
              </v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
    <v-main>
      <v-container fluid class="pa-0">
        <router-view />
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
export default {
  data: () => ({
    appName: "Employee Portal",
    items: [
      { icon: "mdi-table", title: "Home", route: "/" },
      { icon: "mdi-account-plus", title: "New Employee", route: "/add" },
    ],
  }),
};
</script>
