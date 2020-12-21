import axios from "axios";
import omitBy from "lodash/omitBy";
import isNull from "lodash/isNull";

const Backend = axios.create({
  baseURL: process.env.VUE_APP_API_URL,
  headers: {
    "Access-Control-Allow-Origin": process.env.BASE_URL,
  },
});

export default {
  getEmployees: (empRequest) => {
    const params = new URLSearchParams(omitBy(empRequest, isNull));

    return Backend.get(process.env.VUE_APP_CONTEXT_API, {
      params: params,
    });
  },
};
