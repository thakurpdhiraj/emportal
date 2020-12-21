import { createServer, Model, Factory } from "miragejs";
export function makeServer({ environment = "mock" } = {}) {
  let server = createServer({
    environment,

    models: {
      employee: Model,
    },

    factories: {
      employee: Factory.extend({
        name(i) {
          return `Name ${i}`;
        },
        email(i) {
          return `mail${i}@mail.com`;
        },
        joining(i) {
          let dates = ["2020-12-01", "2020-11-01", "2020-03-01", "2020-04-01"];
          return dates[i % dates.length];
        },
        department(i) {
          let dep = ["IT", "BPS"];
          return dep[i % dep.length];
        },
      }),
    },

    seeds(server) {
      server.createList("employee", 25);
    },

    routes() {
      this.namespace = "api";
      this.timing = 2000;
      this.get("/employees", (schema, request) => {
        let result = schema.db.employees.where({});
        let ogLength = result.length;
        let page = Number(request.queryParams.pageKey);
        let size = Number(request.queryParams.size);
        let start = page * size;
        let end = start + size;
        result = result.slice(start, end);
        console.log(ogLength, start, end, page);
        return {
          nextKey: ogLength > end ? page + 1 : null,
          previousKey: start > 0 ? page - 1 : null,
          employees: result,
        };
      });
    },
  });

  return server;
}
