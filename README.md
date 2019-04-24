Ne işe yarar ? 
_______________________________________________________________________________

 Elimizde bir yönetim paneli  var ve bu yönetim paneli  bir activity ile yönetilmekte,
Activity üzerinde  dashboard, mesaj kutusu , bildirim alanları..  kısacası birbirinden farklı 10 view olduğunu düşünelim ,
bu viewler üzerinde uzak sunucudan aldığınız bilgilere göre veya localde kullanıcıların  hangi view'ı hangi sırada ekranda görünmesi
gerektiğini seçtirmek istiyorsunuz.  Gelen seçimlere göre viewleri bir sıraya sokabilirsiniz ve  bunu yaparken animasyonlar kullanabilirsiniz
ayrıca animasyonları değişim durumları geliş sıralarına göre değiştirebilirsiniz geliş sürelerine ve animasyon tiplerine anlık müdahele 
edebilirsiniz . Kısacası bu amaca hizmet etmek için yazılmıştır .


* Dinleme Yapıları 
__________________________________

  * ItemClickListener
  
  Ekran üzerinden hangi view tıkladıysanız o view ile ilgili atadığınız Sıra Numarasını ve View Nesnesini dinler.
  
  * DelayedAllViewListener
  
  Eklemek istediğiniz layout ları sıra numarasına göre ekledikten sonra (viewSorting.addViewToList(1,R.layout.XXXX))
 refreshViewSequentially(); fonksiyonunu çalıştırdığınızda  viewleriniz dolmaya başlar ve onStart(boolean isAdd) tetiklenir .
 
 - onStart(boolean isAdd)  -> ekleme veya silme işlemine göre  (ekleme true silme false) boolean değer döndürür.
 
 view dolumu gerçekleşip bittikten sonra onEnd(boolean isAdd) tetiklenir .
 
 -onEnd(boolean isAdd) -> ekleme veya silme işlemine göre  (ekleme true silme false) boolean değer döndürür.
 
 * ChangingViewListener 
 
 Bir ekleme veya silme gerçekleşirken view üzerinde değişiklik olduğunu bildirir onChange(int rowNumber,boolean isAdd,int position) tektiklenir
 
  - onChange(int rowNumber,boolean isAdd,int position)  - >rowNumber addViewToList(1,R.layout.XXXX) bu fonksiyonda eklediğiniz sıra numarası,
  
  isAdd ekleme ise true silme ise false döner  , position eklenmiş olan listedeki sıra numarası .
  
  
  * Core  fonksiyonları 
  ________________________________________
  
  addViewToList() ->   Eklemek istediğiniz view'ın idesini ve sıra numarasını alarak listeye ekler
  ve  animasyon çalıştırmaz ,view doldurmaz ! 
  
  addViewList() ->  addViewToList gibi çalışır fakat farklı olarak her ekleme işleminde animasyonu çalıştırır view doldurur
  
  addAllView() -> addViewToList() fonksiyonu ile listeye ekleme işlemlerinizi yaptıysanız ve ekrana basmak istiyorsanız bunu çalıştırabilirsiniz .
  
  refreshViewSequentially() -> ekran üzerinde kalan viewleriniz var hepsini siler sıralar ve tekrar doldurur 
  
  refreshView() -> ekran üzerinde kalan viewleriniz var hepsini siler ve tekrar doldurur 
  
  removeAllViewsDelayed() -> view lerinizi animasyonlu bir şekilde siler 
  
  removeAllViews() - > tüm viewleri siler 
  
  removeView() -> sıra numarasına göre view 'i siler 
  
  
  * Boolean Type 
  
   isDelayed = viewler dolarken beklemeli bir şekilde dolsunmu ? 
   isContinueScrool = viewler dolarken scroll otomatik aşağı doğru kaysınmı ?
   
   
   * Eğer sınıf ile işiniz biterse çalıştırmayı unutmayın 
   _______________________________________________________
   
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(viewSorting!=null){ 
            viewSorting.unBind();
            viewSorting=null;
        }
    }
    
    
   * Constructor
   ___________________________
   
   LinearLayout containerView,  Context context Nesnelerini Null gönderirseniz NullPointerException fırlatır !
  
  
  
 
