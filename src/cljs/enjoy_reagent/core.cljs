(ns enjoy-reagent.core
  (:require
   [reagent.dom :as rdom]
   [reagent.core :as r]
   [clerk.core :as clerk]))

(def initial-state
  {:width 200
   :height 250})

;; TODO:
;; Identify when a blue is on top of red
;; Do the calc of red container dynamic discading the initial state value
;; Calculate the difference when a blue box is on top of red box
;; Apply space between the moved blue-box 
(defn red-box [initial-state visible-area]
  [:div.red-box
   {:style {:width (:width initial-state)
            :height (:height initial-state)}
    :on-drag-over
    (fn [e]
      (.preventDefault e))
    :on-drop
    (fn [e]
      (let [id (-> e .-dataTransfer (.getData "text"))
            element (-> js/document (.getElementById id))
            available-zone (-> e .-target)]
        (.appendChild available-zone element)
        (.log js/console e)
        (.log js/console (-> e .-target .getBoundingClientRect))
        (.log js/console (-> element .getBoundingClientRect))
        (swap! visible-area assoc
               :x (- (-> available-zone .getBoundingClientRect .-width) (-> element .getBoundingClientRect .-width))
               :y (- (-> available-zone .getBoundingClientRect .-height) (-> element  .getBoundingClientRect .-height)))
        ))}
   ""])

(defn blue-box [{:keys [id]}]
  (fn []
    [:div.blue-box
     {:id id
      :draggable true
      :on-drag-start
      (fn [e]
        (-> e
            .-dataTransfer
            (.setData "text/plain" (-> e .-target .-id)))
          ;; TODO: in the drag over movement, set origina color
        (set! (.. e -currentTarget -style -backgroundColor) "#c8dfff")
        #_(set! (.. e -currentTarget -style -marginBottom) "5px"))}
     ""]))

(defn current-page []
  (let [visible-area (r/atom {:x (:width initial-state) :y (:height initial-state)})]
    (fn []
      [:<>
       [:div.container
        [:div.left
         [blue-box {:id "bb1"}]
         [blue-box {:id "bb2"}]
         [blue-box {:id "bb3"}]
         [blue-box {:id "bb4"}]
         [blue-box {:id "bb5"}]]
        [:div.right
         [red-box initial-state visible-area]]]
       [:div.description
        [:span (str "Total visible area: " (* (:x @visible-area) (:y @visible-area)) "px")]]])))

;; -------------------------
;; Initialize app

(defn mount-root []
  (rdom/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (clerk/initialize!)
  (mount-root))

(defn ^:dev/after-load reload! []
  (mount-root))
