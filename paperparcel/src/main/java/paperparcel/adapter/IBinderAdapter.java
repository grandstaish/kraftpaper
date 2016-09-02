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

package paperparcel.adapter;

import android.os.IBinder;
import android.os.Parcel;
import android.support.annotation.NonNull;
import paperparcel.TypeAdapter;

/** Default {@link TypeAdapter} for {@link IBinder} types */
public final class IBinderAdapter extends AbstractAdapter<IBinder> {
  public static final IBinderAdapter INSTANCE = new IBinderAdapter();

  @NonNull @Override public IBinder read(@NonNull Parcel source) {
    return source.readStrongBinder();
  }

  @Override protected void write(@NonNull IBinder value, @NonNull Parcel dest, int flags) {
    dest.writeStrongBinder(value);
  }

  private IBinderAdapter() {}
}
