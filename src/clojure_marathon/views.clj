(ns clojure-marathon.views
  (:require 
            [clojure.string :as str]
            [hiccup.page :as page]
            [clojure-marathon.db :as db]
            [ring.util.anti-forgery :as util]))

(defn home-page
  []
  (let [races (db/get-all-races)]
    (page/html5
     [:h1 "Spisak svih trka"]
     [:a {:href "/add-race"} "Dodaj trku"]
     [:table 
      [:tr [:th "id"] [:th "naziv"] [:th "dan"] [:th "mesec"] [:th "godina"] [:th "email"] [:th "opis"] [:th "akcije"]]
      (for [race races]
        [:tr [:td (:id race)] [:td (:naziv race)]  [:td (:dan race)] [:td (:mesec race)] [:td (:godina race)] [:td (:email race)] [:td (:opis race)]
              [:td [:a {:href (str "/edit-race/" (:id race))} "Izmeni"]]
              [:td [:a {:href (str "/delete-race/" (:id race))} "Obrisi"]]
              [:td [:a {:href (str "/runners-list/" (:id race))} "Spisak trkaca"]]
              ])])))

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

(defn edit-race-page
  [id]
  (let [race (first (db/get-race-by-id id))]
    (page/html5
     [:h1 "Izmena trke"]
     [:table
      [:form {:action "/edit-race" :method "POST"}
        (util/anti-forgery-field) 
        [:p "id: " [:input {:type "number" :name "id" :value (:id race) }]]
        [:p "naziv: " [:input {:type "text" :name "naziv" :value (:naziv race) }]]
        [:p "dan: " [:input {:type "number" :name "dan" :value (:dan race) }]]
        [:p "mesec: " [:input {:type "number" :name "mesec" :value (:mesec race) }]]
        [:p "godina: " [:input {:type "number" :name "godina" :value (:godina race) }]]
        [:p "email: " [:input {:type "text" :name "email" :value (:email race) }]]
        [:p "opis: " [:input {:type "text" :name "opis" :value (:opis race) }]]
        [:p [:input {:type "submit" :value "Sacuvaj"}]]]])))

(defn edit-race-result-page
  [{:keys [id naziv dan mesec godina email opis]}]
  (db/edit-race id naziv dan mesec godina email opis)
    (page/html5
     [:h1 "Izmenjena trka"]
     [:a {:href "/races"} "Sve trke"]))

(defn delete-race-page
  [id]
  (db/delete-race id)
    (page/html5
     [:h1 "Obrisana trka"]
     [:a {:href "/races"} "Sve trke"]))

(defn runners-list-page
  [id]
  (let [runners (db/get-all-runners-for-race id)]
    (page/html5
     [:h1 "Spisak trkaca"]
     [:a {:href "/add-runner"} "Dodaj trkaca"]
     [:a {:href "/races"} "Sve trke"]
     [:table 
      [:tr [:th "id"] [:th "ime"] [:th "prezime"] [:th "age"] [:th "broj"] [:th "email"] [:th "trka"]]
      (for [runner runners]
        [:tr [:td (:id runner)] [:td (:ime runner)]  [:td (:prezime runner)] [:td (:age runner)] [:td (:broj runner)] [:td (:email runner)] [:td (:trka runner)]])])))


