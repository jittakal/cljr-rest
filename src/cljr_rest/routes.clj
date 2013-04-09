(ns ^{:author "Jitendra Takalkar"
      :doc "Application core"}
  cljr-rest.routes
  (:use [clojure.tools.logging :only (info error)])
  (:use compojure.core)  
  (:require [compojure.route :as route]
            [cljr-rest.resource.user :as user]))

(defroutes user-routes
  (GET  "/" [] (user/all))
  (POST "/" {body :body} (user/create body))
  (GET  "/:id" [id] (user/by-id id))
  (PUT  "/:id" {id :id body :body} (user/update id body))
  (DELETE "/:id" [id] (user/delete id)))

(defroutes app-routes
  (context "/users" [] user-routes)
  (route/not-found "Not Found"))