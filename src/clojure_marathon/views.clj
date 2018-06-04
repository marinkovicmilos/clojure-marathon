(ns clojure-marathon.views
  (:require 
            [clojure.string :as str]
            [hiccup.page :as page]
            [clojure-marathon.db :as db]
            [ring.util.anti-forgery :as util]))

(defn home-page
  []
  (let [trke (db/get-all-races)]
    (page/html5
     [:h1 "Spisak svih trka"]
     [:a {:href "/add-race"} "Dodaj trku"]
     [:table 
      [:tr [:th "id"] [:th "naziv"] [:th "dan"] [:th "mesec"] [:th "godina"] [:th "email"] [:th "opis"] ]
      (for [trka trke]
        [:tr [:td (:id trka)] [:td (:naziv trka)]  [:td (:dan trka)] [:td (:mesec trka)] [:td (:godina trka)] [:td (:email trka)] [:td (:opis trka)]])])))

(defn add-race-page
  []
  (page/html5
   [:h1 "Dodaj trku"]
   [:form {:action "/add-race" :method "POST"}
    (util/anti-forgery-field) 
    [:p "naziv: " [:input {:type "text" :name "naziv"}]]
    [:p "dan: " [:input {:type "number" :name "dan"}]]
    [:p "mesec: " [:input {:type "number" :name "mesec"}]]
    [:p "godina: " [:input {:type "number" :name "godina"}]]
    [:p "email: " [:input {:type "text" :name "email"}]]
    [:p "opis: " [:input {:type "text" :name "opis"}]]
    [:p [:input {:type "submit" :value "Sacuvaj"}]]]))

(defn add-race-result-page
  [{:keys [naziv dan mesec godina email opis]}]
  (db/add-race naziv dan mesec godina email opis)
    (page/html5
     [:h1 "Dodata trka"]
     [:a {:href "/races"} "Sve trke"]))