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
     [:h1 "Spisak prijavljenih"]
     [:a {:href (str "/add-runner/" id)} "Dodaj trkaca"]
     [:a {:href "/races"} "Sve trke"]
     [:table 
      [:tr [:th "id"] [:th "ime"] [:th "prezime"] [:th "age"] [:th "startni broj"] [:th "email"] [:th "trka"] [:th "akcije"]]
      (for [runner runners]
        [:tr [:td (:id runner)] [:td (:ime runner)]  [:td (:prezime runner)] [:td (:age runner)] [:td (:broj runner)] [:td (:email runner)] [:td (:trka runner)]
            [:td [:a {:href (str "/edit-runner/" (:id runner))} "Izmeni"]]
            [:td [:a {:href (str "/delete-runner/" (:id runner))} "Obrisi"]]
        ])])))

(defn add-runner-page
  [race]
  (page/html5
   [:h1 "Dodaj trkaca"]
   [:form {:action "/add-runner" :method "POST"}
    (util/anti-forgery-field) 
    [:p "ime: " [:input {:type "text" :name "ime"}]]
    [:p "prezime: " [:input {:type "text" :name "prezime"}]]
    [:p "age: " [:input {:type "number" :name "age"}]]
    [:p "startni broj: " [:input {:type "number" :name "broj"}]]
    [:p "email: " [:input {:type "text" :name "email"}]]
    [:p "trka: " [:input {:type "number" :name "trka" :value race}]]
    [:p [:input {:type "submit" :value "Sacuvaj"}]]]))

(defn add-runner-result-page
  [{:keys [ime prezime age broj email trka]}]
  (db/add-runner ime prezime age broj email trka)
    (page/html5
     [:h1 "Dodat trkac"]
     [:a {:href "/races"} "Sve trke"]))

(defn edit-runner-page
  [id]
  (let [runner (first (db/get-runner-by-id id))]
    (page/html5
     [:h1 "Izmena trke"]
     [:table
      [:form {:action "/edit-runner" :method "POST"}
        (util/anti-forgery-field) 
        [:p "id: " [:input {:type "number" :name "id" :value (:id runner) }]]
        [:p "ime: " [:input {:type "text" :name "ime" :value (:ime runner) }]]
        [:p "prezime: " [:input {:type "text" :name "prezime" :value (:prezime runner) }]]
        [:p "age: " [:input {:type "number" :name "age" :value (:age runner) }]]
        [:p "startni broj: " [:input {:type "number" :name "broj" :value (:broj runner) }]]
        [:p "email: " [:input {:type "text" :name "email" :value (:email runner) }]]
        [:p "trka: " [:input {:type "number" :name "trka" :value (:trka runner) }]]
        [:p [:input {:type "submit" :value "Sacuvaj"}]]]])))

(defn edit-runner-result-page
  [{:keys [id ime prezime age broj email trka]}]
  (db/edit-runner id ime prezime age broj email trka)
    (page/html5
     [:h1 "Izmenjen trkac"]
     [:a {:href "/races"} "Sve trke"]))

(defn delete-runner-page
  [id]
  (db/delete-runner id)
    (page/html5
     [:h1 "Izbrisan trkac"]
     [:a {:href "/races"} "Sve trke"]))