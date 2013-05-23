(ns wdays.test.change
  (:use [wdays.change])
  (:require [clojure.data.json :as json])
  ;;(:use [clojure.tools.trace])
  (:use [clojure.test]))

(deftest upd-wday-test
  (let [wd [1.0 2.0 3]
        new-wday (wdays.change/upd-wday wd)]
    (is (every? integer? new-wday))
    (is (= [1 2 3] new-wday))))

(deftest make-req-test
  (let [id "04a47f80137f6853a6e5fa23af2437f1"
        req (wdays.change/make-req id)]
    (is (= req
           {:headers {"X-Editor-Version" "1.0.0"}, :body "{\"type\":\"value\",\"content\":\"04a47f80137f6853a6e5fa23af2437f1\"}"}
           ))))

(def resp
  {:trace-redirects ["http://claudius.ghcg.com:9090/symboldb/symboldb_http:get"], :request-time 772, :status 200, :headers {"server" "inets/5.9", "content-type" "text/html", "date" "Thu, 23 May 2013 20:06:16 GMT", "transfer-encoding" "chunked", "connection" "close"}, :body "{\"type\": \"document\", \"content\": {\"rules\":[{\"description\":\"Default\",\"schedule\":[{\"to\":\"10:55:00\",\"from\":\"00:00:05\"},{\"to\":\"11:55:00\",\"from\":\"11:00:00\"},{\"to\":\"12:55:00\",\"from\":\"12:00:00\"},{\"to\":\"13:55:00\",\"from\":\"13:00:00\"},{\"to\":\"14:55:00\",\"from\":\"14:00:00\"},{\"to\":\"15:55:00\",\"from\":\"15:00:00\"},{\"to\":\"16:55:00\",\"from\":\"16:00:00\"},{\"to\":\"17:55:00\",\"from\":\"17:00:00\"},{\"to\":\"18:55:00\",\"from\":\"18:00:00\"},{\"to\":\"23:55:00\",\"from\":\"19:00:00\"}],\"matches\":[{\"day\":{\"occurrence\":\"*\",\"weekDay\":[1,2,3,4,5,6,7]},\"year\":\"*\",\"month\":\"*\"}]}],\"name\":\"Test Schedule\",\"_id\":\"04a47f80137f6853a6e5fa23af2437f1\",\"timezone\":\"Europe/Moscow\",\"sessionBreak\":\"00:00:00\",\"doctype\":\"schedule\",\"_rev\":\"04d9e9b7c69effa667d3d36fe098d944\"}}"}
  )

(def decoded
  {"rules" [{"description" "Default", "schedule" [{"to" "10:55:00", "from" "00:00:05"} {"to" "11:55:00", "from" "11:00:00"} {"to" "12:55:00", "from" "12:00:00"} {"to" "13:55:00", "from" "13:00:00"} {"to" "14:55:00", "from" "14:00:00"} {"to" "15:55:00", "from" "15:00:00"} {"to" "16:55:00", "from" "16:00:00"} {"to" "17:55:00", "from" "17:00:00"} {"to" "18:55:00", "from" "18:00:00"} {"to" "23:55:00", "from" "19:00:00"}], "matches" [{"day" {"occurrence" "*", "weekDay" [1.1 2.2 3.0 4 5 6 7]}, "year" "*", "month" "*"} {"day" {"occurrence" "*", "weekDay" [1.3 3.0 5.1 7.0]}, "year" "*", "month" "*"}]}], "name" "Test Schedule", "_id" "04a47f80137f6853a6e5fa23af2437f1", "timezone" "Europe/Moscow", "sessionBreak" "00:00:00", "doctype" "schedule", "_rev" "04d9e9b7c69effa667d3d36fe098d944"}
  )

(deftest upd-match-test
  (let [match {"day" {"occurrence" "*",
                      "weekDay" [1.0 2.3 3.1 4 5]},
               "year" "*", "month" "*"}
        exp {"day" {"occurrence" "*",
                    "weekDay" [1 2 3 4 5]},
             "year" "*", "month" "*"}
        act (wdays.change/upd-match match)]
    (is (= exp act))))

(deftest upd-rule-test
  (let [exp {"description" "Default", "schedule" [{"to" "10:55:00", "from" "00:00:05"} {"to" "11:55:00", "from" "11:00:00"} {"to" "12:55:00", "from" "12:00:00"} {"to" "13:55:00", "from" "13:00:00"} {"to" "14:55:00", "from" "14:00:00"} {"to" "15:55:00", "from" "15:00:00"} {"to" "16:55:00", "from" "16:00:00"} {"to" "17:55:00", "from" "17:00:00"} {"to" "18:55:00", "from" "18:00:00"} {"to" "23:55:00", "from" "19:00:00"}], "matches" [{"day" {"occurrence" "*", "weekDay" [1 2 3 4 5 6 7]}, "year" "*", "month" "*"} {"day" {"occurrence" "*", "weekDay" [1 3 5 7]}, "year" "*", "month" "*"}]}
        rules (get decoded "rules")
        rule (first rules)
        act (wdays.change/upd-rule rule)]
    (is (= exp act))))

