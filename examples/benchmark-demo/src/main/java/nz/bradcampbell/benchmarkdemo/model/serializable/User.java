/*
 * Copyright (C) 2016 Bradley Campbell.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nz.bradcampbell.benchmarkdemo.model.serializable;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
  public String id;
  public int index;
  public String guid;
  @SerializedName("is_active") public boolean isActive;
  public String balance;
  @SerializedName("picture") public String pictureUrl;
  public int age;
  public Name name;
  public String company;
  public String email;
  public String address;
  public String about;
  public String registered;
  public double latitude;
  public double longitude;
  public List<String> tags;
  public List<Integer> range;
  public List<Friend> friends;
  public List<Image> images;
  public String greeting;
  @SerializedName("favorite_fruit") public String favoriteFruit;
  @SerializedName("eye_color") public String eyeColor;
  public String phone;
}
