(ns clojure-marathon.views
  (:require 
            [clojure.string :as str]
            [hiccup.page :as page]
            [ring.util.anti-forgery :as util]))

(defn home-page
  []
  (page/html5
    [:h1 "Spisak svih trka"]
    [:a {:href "/add-race"} "Dodaj trku"]
    [:table
      [:tr [:th "id"] [:th "naziv"] [:th "dan"] [:th "mesec"] [:th "godina"] [:th "email"] [:th "opis"]]]))

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


