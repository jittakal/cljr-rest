(ns ^{:author "Jitendra Takalkar"
      :doc "Users table related database access functions"}
  cljr-rest.db.user
  (:use cljr-rest.db.core)
  (:require [clojure.java.jdbc :as sql]))

(defn get-all-users 
  "Get list of all users"
  []
  (with-conn-trans
    (sql/with-query-results results
                            ["select * from users"]
                            (into [] results))))

(defn get-user
  [id]
  (with-conn-trans
    (sql/with-query-results results
                            ["select * from users where id = ?" id]
                            (first results))))

(defn create-new-user 
  [iuser]
  (let [id (uuid)]
    (with-conn-trans
      (let [user (assoc iuser "id" id)]
        (sql/insert-record :users user)))
    (get-user id)))

(defn update-user
  [id iuser]
  (with-conn-trans
    (let [user (assoc iuser "id" id)]
      (sql/update-values :users ["id=?" id] user)))
  (get-user id))

(defn delete-user 
  [id]
  (with-conn-trans
    (sql/delete-rows :users ["id=?" id])))

