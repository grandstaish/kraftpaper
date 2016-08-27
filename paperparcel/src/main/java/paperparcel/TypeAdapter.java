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

package paperparcel;

import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A custom method for reading/writing a Type
 *
 * @param <T> The Type to override the default reading/writing functionality for
 */
public interface TypeAdapter<T> {

  /**
   * Creates a new instance of the desired Type by reading values from the Parcel {@code inParcel}
   *
   * @param source The {@link Parcel} which contains the values of {@code T}
   * @return A new object based on the values in {@code inParcel}.
   */
  @Nullable T readFromParcel(@NonNull Parcel source);

  /**
   * Writes {@code value} to the Parcel {@code outParcel}
   *
   * @param value The object to be written to the {@link Parcel}
   * @param dest The {@link Parcel} which will contain the value of {@code T}
   * @param flags Additional flags about how the object should be written. May be 0 or
   * {@link android.os.Parcelable#PARCELABLE_WRITE_RETURN_VALUE}.
   */
  void writeToParcel(@Nullable T value, @NonNull Parcel dest, int flags);
}