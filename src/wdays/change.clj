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

(defn upd-instrument [instrument]
  (assert false "not implemented")
  )

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