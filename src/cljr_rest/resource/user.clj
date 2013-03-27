(ns ^{:author "Jitendra Takalkar"
      :doc "Application core"}
  cljr-rest.resource.user   
  (:use compojure.core)
  (:use ring.util.response)
  (:use [clojure.tools.logging :only (info error)])
  (:require [cljr-rest.db.user :as db-user]))

(defn by-id
  "Get User"
  [id]
  (info (str *ns* " # " id))
  (let [result (db-user/get-by-id id)]
    (cond
     (empty? result)  (status {:body (str "User having id " id " does not exists.")} 404)
     (contains? result :error-code) (status result 500)
     :else (response result))))

(defn all
  "Get All users"
  []
  (response (db-user/get-all)))


(defn create
  [iuser]
  (let [result (db-user/create-new iuser)]
    (cond     
     (empty? result) (status result 204)
     (contains? result :error-code) (status result 500)
     :else (response result))))

(defn validate-create-user
  [iuser]
  (let [name (:name iuser)]
    (if (not= name "Jitendra")
      {:status 400
       :msg "Name is not equals to Jitendra"}
      (create iuser))))

(defn update
  [id iuser]
  (let [result (db-user/update id iuser)]
    (cond     
     (empty? result) (status result 404)
     (contains? result :error-code) (status result 500)
     :else (response result))))

(defn delete
  "Delete user"
  [id]
  (let [result (db-user/delete id)]
    (cond     
     (empty? result) (status result 204)
     (contains? result :error-code) (status result 500)
     :else (response result))))
