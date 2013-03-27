(ns ^{:author "Jitendra Takalkar"
      :doc "Application core"}
  cljr-rest.core
  (:require [compojure.handler :as handler]
            [ring.middleware.json :as middleware]            
            [cljr-rest.routes :as routes]))
;;  (:gen-class))

;; Main Function
;; (defn -main
;;  "I don't do a whole lot ... yet."
;;  [& args]
;;  (println "Hello, World!"))

(def app
  (-> (handler/api routes/app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)))  
