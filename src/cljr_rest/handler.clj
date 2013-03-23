(ns cljr-rest.handler  
  (:use compojure.core)
  (:use cheshire.core)
  (:use ring.util.response)
  (:require [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [compojure.route :as route]
            [cljr-rest.db.user :as u]))

(defroutes app-routes
  (context "/users" [] (defroutes users-routes
                         (GET  "/" [] (response (u/get-all-users)))
                         (POST "/" {body :body} (u/create-new-user body))
                         (context "/:id" [id] (defroutes user-routes
                                                    (GET    "/" [] (let [result (u/get-user id)]
                                                                     (cond 
                                                                      (empty? result) {:status 204}
                                                                      :else (response result))))
                                                    (PUT    "/" {body :body} (u/update-user id body))
                                                    (DELETE "/" [] (u/delete-user id))))))
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)))  
