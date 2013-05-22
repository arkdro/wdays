(ns wdays.change
  (:require [clojure.data.json :as json])
  (:require [clj-http.client :as client])
  )

(defn json [&a] nil)

(defn make-req [id]
  (let [hdr {"X-Editor-Version" "1.0.0"}
        data (json/write-str {"type" "value" "content" id})]
    {:headers hdr
     :body data}))

(defn get-current [url id]
  (let [pars (make-req id)
        response (client/post url pars)]
    (get-in response [:body :content])))

(defn upd-wday [wday]
  (cond (seq? wday) (map int wday)
        (vector? wday) (map int wday)
        :default wday))

(defn upd-match [match]
  (let [wday (get-in match ["day" "weekDay"])
        new-wday (upd-wday wday)]
    (assoc-in match ["day" "weekDay"] new-wday)))

(defn upd-matches [matches]
  (map upd-match matches))

(defn upd-rule [rule]
  (let [matches (get rule "matches")
        new-matches (upd-matches matches)]
    (assoc rule "matches" new-matches)))

(defn upd-rules [rules]
  (map upd-rule rules))

(defn upd-instrument [instrument]
  (let [rules (get instrument "rules")
        new-rules (upd-rules rules)]
    (assoc instrument "rules" new-rules)))

(defn put-instrument [url id updated]
  (assert false "not implemented")
  )

(defn change [{:keys [url id] :as opts}]
  (let [
        _ (print "\n2, opts:" opts "\nurl:" url "id:" id)
        instrument (get-current url id)
        updated (upd-instrument instrument)]
    put-instrument url id updated
    ;;(print "\ncur:" data)
    ))