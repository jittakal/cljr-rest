(ns ^{:author "Jitendra Takalkar"
      :doc "Database core or common functions"}
  cljr-rest.db.core
  (:import com.mchange.v2.c3p0.ComboPooledDataSource)
  (:require [clojure.java.jdbc :as sql]))

(defn uuid 
  [] 
  (str (java.util.UUID/randomUUID)))

(def db-config {:classname "org.h2.Driver" ; must be in classpath
                :subprotocol "h2"
                :subname "file:/home/jittakal/Data/cljr-rest"
                ; Any additional keys are passed to the driver
                ; as driver-specific properties.
                :user     "sa"
                :password ""})

(defn pool
  [config]
  (let [cpds (doto (ComboPooledDataSource.)
               (.setDriverClass (:classname config))
               (.setJdbcUrl (str "jdbc:" (:subprotocol config) ":" (:subname config)))
               (.setUser (:user config))
               (.setPassword (:password config))
               (.setMaxPoolSize 5)
               (.setMinPoolSize 1)
               (.setInitialPoolSize 1))]
    {:datasource cpds}))

(def pooled-db (delay (pool db-config)))

(defn db-connection
  []
  @pooled-db)

(defmacro with-conn-trans
  "Macro to append with-connection and transaction"
  [& body]
  `(sql/with-connection
    (db-connection)
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
     [:id "varchar(256)" "PRIMARY KEY"]
     [:title "varchar(256)" "NOT NULL"]
     [:body "varchar(1024)" "NOT NULL"])))

(defn create-blogs
  "Create a blogs table which holds blog information"
  []
  (with-conn-trans
    (sql/create-table
     :blogs
     [:id "varchar(256)" "PRIMARY KEY"]
     [:title "varchar(256)" "NOT NULL"]
     [:body "varchar(256)" "NOT NULL"])))

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
     [:id "varchar(256)" "PRIMARY KEY"]
     [:name "varchar(256)" "NOT NULL"])))

(defn create-users
  "Create a users table which hold users information"
  []
  (with-conn-trans
    (sql/create-table
     :users
     [:id "varchar(256)" "PRIMARY KEY"]
     [:name "varchar(256)" "NOT NULL"])))