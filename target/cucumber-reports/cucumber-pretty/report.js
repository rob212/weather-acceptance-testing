$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("dummy.feature");
formatter.feature({
  "line": 1,
  "name": "A dummy feature to test setup works",
  "description": "",
  "id": "a-dummy-feature-to-test-setup-works",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 3,
  "name": "Walking Skeleton",
  "description": "",
  "id": "a-dummy-feature-to-test-setup-works;walking-skeleton",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 4,
  "name": "some fake setup",
  "keyword": "Given "
});
formatter.step({
  "line": 5,
  "name": "we some 1 and 1",
  "keyword": "When "
});
formatter.step({
  "line": 6,
  "name": "we get 2",
  "keyword": "Then "
});
formatter.match({
  "location": "DummySteps.some_fake_setup()"
});
formatter.result({
  "duration": 105826358,
  "error_message": "cucumber.api.PendingException: TODO: implement me\n\tat step_definitions.DummySteps.some_fake_setup(DummySteps.java:13)\n\tat ✽.Given some fake setup(dummy.feature:4)\n",
  "status": "pending"
});
formatter.match({
  "arguments": [
    {
      "val": "1",
      "offset": 8
    },
    {
      "val": "1",
      "offset": 14
    }
  ],
  "location": "DummySteps.we_some_and(int,int)"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "arguments": [
    {
      "val": "2",
      "offset": 7
    }
  ],
  "location": "DummySteps.we_get(int)"
});
formatter.result({
  "status": "skipped"
});
});