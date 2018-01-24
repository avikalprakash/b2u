package com.lueinfo.bshop.Adapter;

/**
 * Created by Fujitsu on 15/04/2017.
 */

public class PojoClass {

       private String name;
       private int image;

               public PojoClass() {
          }
      public PojoClass(int image, String name) {

          this.image = image;
          this.name = name;

           }

                public String getName() {
                return name;
            }

                public void setName(String name) {
                this.name = name;
            }

                public int getImage() {
                return image;
            }

                public void setImage(int image) {
                this.image = image;
            }
    }




