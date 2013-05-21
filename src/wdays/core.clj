(ns wdays.core
  {:doc "get doc by id, make week days integers, post updated doc"}
  (:use clj-getopts.core)
  (:require wdays.change)
  (:gen-class)
  )

(defn -main [& args]
  (let [opts (getopts (options "iu?" {:url :arg :id :arg}) args)
        _ (print "args:" args "\nopts:" opts)]
    (wdays.change/change opts)))

