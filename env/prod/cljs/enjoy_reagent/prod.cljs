(ns enjoy-reagent.prod
  (:require [enjoy-reagent.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
