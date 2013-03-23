(ns ^{:author "Jitendra Takalkar"
      :doc "Database core or common functions"}
  cljr-rest.db.core
  (:require [clojure.java.jdbc :as sql]))

(def db {:classname "org.h2.Driver" ; must be in classpath
         :subprotocol "h2"
         :subname "file:/home/jittakal/Data/cljr-rest"
         ; Any additional keys are passed to the driver
         ; as driver-specific properties.
         :user     "sa"
         :password ""})

(defmacro with-conn-trans
  "Macro to append with-connection and transaction"
  [& body]
  `(sql/with-connection
    db
    (sql/transaction ~@body)))

(defn drop-blogs
  "Drop blogs table"
  []
  (with-conn-trans 
    (try
      (sql/drop-table :blogs)
      (catch Exception _))))

(defn create-blogs-ddl
  "Blogs table create ddl"
  []
  (with-conn-trans
    (sql/create-table-ddl
     :blogs
     [:id :bigint "PRIMARY KEY" "AUTO_INCREMENT"]
     [:title "varchar(255)" "NOT NULL"]
     [:body "varchar(255)" "NOT NULL"])))

(defn create-blogs
  "Create a blogs table which holds blog information"
  []
  (with-conn-trans
    (sql/create-table
     :blogs
     [:id :int "PRIMARY KEY" "AUTO_INCREMENT"]
     [:title "varchar(255)" "NOT NULL"]
     [:body "varchar(255)" "NOT NULL"])))

(defn drop-users
  "Drop users table"
  []
  (with-conn-trans
    (try
      (sql/drop-table :users)
      (catch Exception _))))

(defn create-users-ddl
  "Users table Create ddl"
  []
  (with-conn-trans
    (sql/create-table-ddl
     :users
     [:id :int "PRIMARY KEY" "AUTO_INCREMENT"]
     [:name "varchar(255)" "NOT NULL"])))

(defn create-users
  "Create a users table which hold users information"
  []
  (with-conn-trans
    (sql/create-table
     :users
     [:id :int "PRIMARY KEY" "AUTO_INCREMENT"]
     [:name "varchar(255)" "NOT NULL"])))