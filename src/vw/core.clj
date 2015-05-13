(ns vw.core
  (:import
   (com.sun.jna Native Function NativeLibrary)
   (org.bridj Pointer BridJ)
   (vw_c_wrapper Vw_c_wrapperLibrary)))

(defn load-vw []
  (NativeLibrary/getInstance "vw"))

(defn load-al []
  (NativeLibrary/getInstance "allreduce"))

(def vw2 (BridJ/getNativeLibrary "vw"))
(def vw-lib (load-vw))
(def al-lib (load-al))

(defn cstring [s]
  (Pointer/pointerToCString s))

(defn init-vw [args]
  (Vw_c_wrapperLibrary/VW_InitializeA (Pointer/pointerToCString (or args ""))))

(defn load-sample []
  (let [vw (init-vw "/home/adam/src/vw/rcv1/rcv1.train.vw.gz --cache_file cache_train -f r_temp")]
    ;; Start parsing samples
    (Vw_c_wrapperLibrary/VW_StartParser vw false)
    (loop [ex (Vw_c_wrapperLibrary/VW_GetExample vw)]
      (when ex
        (Vw_c_wrapperLibrary/VW_Learn vw ex)
        (Vw_c_wrapperLibrary/VW_FinishExample vw ex)
        (recur (Vw_c_wrapperLibrary/VW_GetExample vw))))
    (Vw_c_wrapperLibrary/VW_EndParser vw)
    vw ))

(def sample-line
  "| 20:9.9423851e-02 54:1.3187788e-02")

(defn unlog [x]
  (/ 1.0 (+ 1.0 (Math/exp (* -1.0 x)))))

(defn predict [vw ex-str]
  (let [ex (Vw_c_wrapperLibrary/VW_ReadExampleA vw (cstring ex-str))
        pred (Vw_c_wrapperLibrary/VW_Predict vw ex)]
    (Vw_c_wrapperLibrary/VW_FinishExample vw ex)
    (unlog pred)))
