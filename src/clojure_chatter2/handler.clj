(ns clojure-chatter2.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [hiccup.page :as page]
            [hiccup.form :as form]))

      (defn generate-message-view
        "This generates the HTML for displaying messages"
        [messages]
        (page/html5
         [:head
          [:title "chatter"]]
         [:body
          [:h1 "Our Chat App"]
          [:p
          (form/form-to
            [:post "/"]
            "Name: " (form/text-field "name")
            "Message: " (form/text-field "msg")
            (form/submit-button "Submit"))]
          [:p
          [:table
            (map (fn [m] [:tr [:td (:name m)] [:td (:message m)]]) messages)]]]))


(def chat-messages [{:name "blue" :message "hello, world"}
                    {:name "red" :message "red is my favorite color"}
                    {:name "green" :message "green makes it go faster"}])

(defroutes app-routes
  (GET "/" [] (generate-message-view chat-messages))
  (POST "/" [] (generate-message-view chat-messages))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
