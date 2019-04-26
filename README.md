
Gradle :

    allprojects {
        repositories {
            jcenter()
            maven { url "https://jitpack.io" }
        }
   }
   dependencies {
   
     implementation 'com.github.yenenahmet:CustomViewRanking:0.1.0'
     
   }

---------------------------------------------



* Ne işe yarar ? 
_______________________________________________________________________________

 Elimizde bir yönetim paneli  var ve bu yönetim paneli  bir activity ile yönetilmekte,
Activity üzerinde  gösterge paneli, mesaj kutusu , bildirim alanları...  kısacası birbirinden farklı 10 görünüm olduğunu düşünelim ,
bu görünümler üzerinde uzak sunucudan aldığınız bilgilere göre veya yerelde kullanıcıların  hangi görünümü hangi sırada ekranda görünmesi
gerektiğini seçtirmek istiyorsunuz.  Gelen seçimlere göre görünümleri bir sıraya sokabilirsiniz ve  bunu yaparken animasyonlar kullanabilirsiniz
ayrıca animasyonları değişim durumları geliş sıralarına göre değiştirebilirsiniz geliş sürelerine ve animasyon tiplerine anlık müdahele 
edebilirsiniz . 
Kısacası bu amaca hizmet etmek için yazılmıştır .


* Dinleme Yapıları 
__________________________________

  - ItemClickListener
  
  Ekran üzerinden hangi görünüme tıkladıysanız o görünüm ile ilgili atadığınız Sıra Numarasını ve görünüm nesnesini dinler.
  
  - DelayedAllViewListener
  
  Eklemek istediğiniz görünümleri sıra numarasına göre ekledikten sonra (viewSorting.addViewToList(1,R.layout.XXXX))
 refreshViewSequentially(); fonksiyonunu çalıştırdığınızda  görünüm dolmaya başlar ve onStart(boolean isAdd) tetiklenir .
 
 - onStart(boolean isAdd)  -> ekleme veya silme işlemine göre  (ekleme true silme false) boolean değer döndürür.
 
 görünüm dolumu gerçekleşip bittikten sonra onEnd(boolean isAdd) tetiklenir .
 
 -onEnd(boolean isAdd) -> ekleme veya silme işlemine göre  (ekleme true silme false) boolean değer döndürür.
 
 - ChangingViewListener 
 
 Bir ekleme veya silme gerçekleşirken görünüm üzerinde değişiklik olduğunu bildirir onChange(int rowNumber,boolean isAdd,int position) tektiklenir
 
  - onChange(int rowNumber,boolean isAdd,int position)  - >rowNumber addViewToList(1,R.layout.XXXX) bu fonksiyonda eklediğiniz sıra numarası,
  
  isAdd ekleme ise true silme ise false döner  , position eklenmiş olan listedeki sıra numarası .
  
  
  * Core  fonksiyonları 
  ________________________________________
  
  addViewToList() ->   Eklemek istediğiniz görünümün id'sini ve sıra numarasını alarak listeye ekler
  ve  animasyon çalıştırmaz ,view doldurmaz ! 
  
  addViewList() ->  addViewToList gibi çalışır fakat farklı olarak her ekleme işleminde animasyonu çalıştırır view doldurur
  
  addAllView() -> addViewToList() fonksiyonu ile listeye ekleme işlemlerinizi yaptıysanız ve ekrana basmak istiyorsanız bunu çalıştırabilirsiniz .
  
  refreshViewSequentially() -> ekran üzerinde kalan görünümlerin hepsini siler, sıralar ve tekrar doldurur 
  
  refreshView() -> ekran üzerinde kalan görünümlerin hepsini siler ve tekrar doldurur. 
  
  removeAllViewsDelayed() -> görünümlerinizi animasyonlu bir şekilde siler 
  
  removeAllViews() - > tüm görünümleri siler 
  
  removeView() -> sıra numarasına göre görünümü siler 
  
  
  * Boolean Type 
  
   isDelayed = görünüm dolarken beklemeli bir şekilde dolsun mu ? 
   
   isContinueScrool = görünümler dolarken kayrdıma otomatik aşağı doğru kaysın mı ?
   
   
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
   
   
   Görünüm  -> View 
  
  
  
 
