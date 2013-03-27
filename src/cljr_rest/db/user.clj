(ns ^{:author "Jitendra Takalkar"
      :doc "Users table related database access functions"}
  cljr-rest.db.user
  (:use cljr-rest.db.core)
  (:use cljr-rest.common)  
  (:require [clojure.java.jdbc :as sql]))

(defn get-all
  "Get list of all users"
  []
  (with-conn-trans
    (sql/with-query-results results
                            ["select * from users"]
                            (into [] results))))
(defn get-by-id
  [id]
  (try
    (with-conn-trans
      (sql/with-query-results results
                              ["select * from users where id = ?" id]
                              (first results)))
  (catch Exception e
    (err-handler "SQL-100" e))))

(defn create-new
  [iuser]
  (let [id (uuid)]
    (try
      (with-conn-trans
        (let [user (assoc iuser "id" id)]
          (sql/insert-record :users user)))
      (get-by-id id)
      (catch Exception e
        (err-handler "SQL-100" e)))))

(defn update
  [id iuser]
  (with-conn-trans
    (let [user (assoc iuser "id" id)]
      (sql/update-values :users ["id=?" id] user)))
  (get-by-id id))

(defn delete
  [id]
  (with-conn-trans
    (sql/delete-rows :users ["id=?" id])))

