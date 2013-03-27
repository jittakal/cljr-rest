(ns ^{:author "Jitendra Takalkar"
      :doc "Application core"}
  cljr-rest.routes
  (:use [clojure.tools.logging :only (info error)])
  (:use compojure.core)  
  (:require [compojure.route :as route]
            [cljr-rest.resource.user :as user]))

(defroutes user-routes
  (GET  "/" [] (user/get-all-users-res))
  (POST "/" {body :body} (user/create-user-res body))
  (GET  "/:id" [id] (user/get-user-res id))
  (PUT  "/:id" {id :id body :body} (user/update-user-res id body))
  (DELETE "/:id" [id] (user/delete-user-res id)))

(defroutes app-routes
  (context "/users" [] user-routes)
  (route/not-found "Not Found"))
