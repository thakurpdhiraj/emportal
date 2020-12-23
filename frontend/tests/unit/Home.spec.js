import { shallowMount, createLocalVue } from "@vue/test-utils";
import Vuetify from "vuetify";
import { expect } from "chai";
import Home from "@/views/Home.vue";

describe("Home.vue", () => {
  const localVue = createLocalVue();
  let vuetify;

  beforeEach(() => {
    vuetify = new Vuetify();
  });

  const mountFunction = (options) => {
    return shallowMount(Home, {
      localVue,
      vuetify,
      ...options,
    });
  };

  it("form should be visible on initial load", () => {
    const wrapper = mountFunction();
    expect(wrapper.vm.formVisible).to.be.true;
    expect(wrapper.find("._form_row").isVisible()).to.be.true;
    expect(wrapper.find("._form_refine_row").isVisible()).to.be.false;
  });

  it("form should be hidden on click of submit", async () => {
    const wrapper = mountFunction({
      data: () => ({
        valid: true,
      }),
    });
    await wrapper.find("#submitBtn").vm.$emit("click");
    expect(wrapper.vm.formVisible).to.be.false;
    expect(wrapper.find("._form_row").isVisible()).to.be.false;
    expect(wrapper.find("._form_refine_row").isVisible()).to.be.true;
  });

  it("form should be visible on click of refine search", async () => {
    const wrapper = mountFunction({
      data: () => ({
        formVisible: false,
      }),
    });
    await wrapper.find("#refineBtn").vm.$emit("click");
    expect(wrapper.vm.formVisible).to.be.true;
    expect(wrapper.find("._form_row").isVisible()).to.be.true;
    expect(wrapper.find("._form_refine_row").isVisible()).to.be.false;
  });

  it("clear form data on click of reset", async () => {
    const wrapper = mountFunction({
      data: () => ({
        name: "n",
        email: "em",
        department: "IT",
        fromDate: "2020-12-23",
        toDate: "2020-12-23",
      }),
    });

    await wrapper.find("#resetBtn").vm.$emit("click");
    expect(wrapper.vm.name).to.equal(null);
    expect(wrapper.vm.email).to.equal(null);
    expect(wrapper.vm.toDate).to.equal(null);
    expect(wrapper.vm.fromDate).to.equal(null);
    expect(wrapper.vm.department).to.equal(null);
  });
});
