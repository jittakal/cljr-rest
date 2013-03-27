(ns ^{:author "Jitendra Takalkar"
      :doc "Application core"}
  cljr-rest.resource.user   
  (:use compojure.core)
  (:use ring.util.response)
  (:use [clojure.tools.logging :only (info error)])
  (:require [cljr-rest.db.user :as db-user]))

(defn get-user-res
  "Get User"
  [id]
  (info (str *ns* " # " id))
  (let [result (db-user/get-user id)]
    (cond
     (empty? result)  (status {:body (str "User having id " id " does not exists.")} 404)
     (contains? result :error-code) (status result 500)
     :else (response result))))

(defn get-all-users-res
  "Get All users"
  []
  (response (db-user/get-all-users)))


(defn create-user-res
  [iuser]
  (let [result (db-user/create-new-user iuser)]
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
      (create-user-res iuser))))

(defn update-user-res
  [id iuser]
  (let [result (db-user/update-user id iuser)]
    (cond     
     (empty? result) (status result 204)
     (contains? result :error-code) (status result 500)
     :else (response result))))

(defn delete-user-res
  "Delete user"
  [id]
  (let [result (db-user/delete-user id)]
    (cond     
     (empty? result) (status result 204)
     (contains? result :error-code) (status result 500)
     :else (response result))))
