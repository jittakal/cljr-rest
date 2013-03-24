(ns cljr-rest.handler  
  (:use compojure.core)
  (:use cheshire.core)
  (:use ring.util.response)
  (:require [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [compojure.route :as route]
            [cljr-rest.db.user :as u]))

(defn response-create-user
  [iuser]
  (let [result (try
                 (u/create-new-user iuser)
                 (catch Exception e
                   {:status 500
                    :msg (.getMessage e)}))]
    (cond
     (empty? result) {:status 204}
     (= 500 (:status result)) result
     :else (response result))))

(defn validate-create-user
  [iuser]
  (let [name (:name iuser)]
    (if (not= name "Jitendra")
      {:status 400
       :msg "Name is not equals to Jitendra"}
      (response-create-user iuser))))

(validate-create-user {:id "abc-xyz-lmn" :name "Jitendra"})

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
