package nz.bradcampbell.dataparcel;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;
import static java.util.Arrays.asList;

public class ListTests {

  @Test public void listOfParcelableTypesTest() throws Exception {
    JavaFileObject source = JavaFileObjects.forSourceString("test.Test", Joiner.on('\n').join(
        "package test;",
        "import nz.bradcampbell.dataparcel.DataParcel;",
        "import java.util.List;",
        "@DataParcel",
        "public final class Test {",
        "private final List<Integer> testList;",
        "public Test(List<Integer> testList) {",
        "this.testList = testList;",
        "}",
        "public List<Integer> component1() {",
        "return this.testList;",
        "}",
        "}"
    ));

    JavaFileObject expectedSource = JavaFileObjects.forSourceString("test/TestParcel", Joiner.on('\n').join(
        "package test;",
        "import android.os.Parcel;",
        "import android.os.Parcelable;",
        "import java.lang.Integer;",
        "import java.lang.Override;",
        "import java.util.List;",
        "public class TestParcel implements Parcelable {",
        "public static final Parcelable.Creator<TestParcel> CREATOR = new Parcelable.Creator<TestParcel>() {",
        "@Override public TestParcel createFromParcel(Parcel in) {",
        "return new TestParcel(in);",
        "}",
        "@Override public TestParcel[] newArray(int size) {",
        "return new TestParcel[size];",
        "}",
        "};",
        "private final Test data;",
        "private TestParcel(Test data) {",
        "this.data = data;",
        "}",
        "private TestParcel(Parcel in) {",
        "List<Integer> component1 = null;",
        "component1 = (List<Integer>) in.readArrayList(getClass().getClassLoader());",
        "this.data = new Test(component1);",
        "}",
        "public static final TestParcel wrap(Test data) {",
        "return new TestParcel(data);",
        "}",
        "public Test getContents() {",
        "return data;",
        "}",
        "@Override public int describeContents() {",
        "return 0;",
        "}",
        "@Override public void writeToParcel(Parcel dest, int flags) {",
        "List<Integer> component1 = data.component1();",
        "dest.writeList(component1);",
        "}",
        "}"
    ));

    assertAbout(javaSource()).that(source)
        .processedWith(new DataParcelProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(expectedSource);
  }

  @Test public void listOfDataTypesTest() throws Exception {
    JavaFileObject dataClassRoot = JavaFileObjects.forSourceString("test.Root", Joiner.on('\n').join(
        "package test;",
        "import nz.bradcampbell.dataparcel.DataParcel;",
        "import java.util.List;",
        "@DataParcel",
        "public final class Root {",
        "private final List<Child> child;",
        "public Root(List<Child> child) {",
        "this.child = child;",
        "}",
        "public List<Child> component1() {",
        "return this.child;",
        "}",
        "}"
    ));

    JavaFileObject dataClassChild = JavaFileObjects.forSourceString("test.Child", Joiner.on('\n').join(
        "package test;",
        "public final class Child {",
        "private final Integer test;",
        "public Child(Integer test) {",
        "this.test = test;",
        "}",
        "public Integer component1() {",
        "return this.test;",
        "}",
        "}"
    ));

    JavaFileObject rootParcel = JavaFileObjects.forSourceString("test/RootParcel", Joiner.on('\n').join(
        "package test;",
        "import android.os.Parcel;",
        "import android.os.Parcelable;",
        "import java.lang.Override;",
        "import java.util.ArrayList;",
        "import java.util.List;",
        "public class RootParcel implements Parcelable {",
        "public static final Parcelable.Creator<RootParcel> CREATOR = new Parcelable.Creator<RootParcel>() {",
        "@Override public RootParcel createFromParcel(Parcel in) {",
        "return new RootParcel(in);",
        "}",
        "@Override public RootParcel[] newArray(int size) {",
        "return new RootParcel[size];",
        "}",
        "};",
        "private final Root data;",
        "private RootParcel(Root data) {",
        "this.data = data;",
        "}",
        "private RootParcel(Parcel in) {",
        "List<Child> component1 = null;",
        "List<ChildParcel> component1Wrapped = (List<ChildParcel>) in.readArrayList(getClass().getClassLoader());",
        "component1 = new ArrayList<>(component1Wrapped.size());",
        "for (ChildParcel _component1Wrapped : component1Wrapped) {",
        "Child _component1 = null;",
        "_component1 = _component1Wrapped.getContents();",
        "component1.add(_component1);",
        "}",
        "this.data = new Root(component1);",
        "}",
        "public static final RootParcel wrap(Root data) {",
        "return new RootParcel(data);",
        "}",
        "public Root getContents() {",
        "return data;",
        "}",
        "@Override public int describeContents() {",
        "return 0;",
        "}",
        "@Override public void writeToParcel(Parcel dest, int flags) {",
        "List<Child> component1 = data.component1();",
        "List<ChildParcel> component1Wrapped = new ArrayList<>(component1.size());",
        "for (Child component1Item : component1) {",
        "ChildParcel _component1 = ChildParcel.wrap(component1Item);",
        "component1Wrapped.add(_component1);",
        "}",
        "dest.writeList(component1Wrapped);",
        "}",
        "}"
    ));

    JavaFileObject childParcel = JavaFileObjects.forSourceString("test/ChildParcel", Joiner.on('\n').join(
        "package test;",
        "import android.os.Parcel;",
        "import android.os.Parcelable;",
        "import java.lang.Integer;",
        "import java.lang.Override;",
        "public class ChildParcel implements Parcelable {",
        "public static final Parcelable.Creator<ChildParcel> CREATOR = new Parcelable.Creator<ChildParcel>() {",
        "@Override public ChildParcel createFromParcel(Parcel in) {",
        "return new ChildParcel(in);",
        "}",
        "@Override public ChildParcel[] newArray(int size) {",
        "return new ChildParcel[size];",
        "}",
        "};",
        "private final Child data;",
        "private ChildParcel(Child data) {",
        "this.data = data;",
        "}",
        "private ChildParcel(Parcel in) {",
        "Integer component1 = null;",
        "component1 = in.readInt();",
        "this.data = new Child(component1);",
        "}",
        "public static final ChildParcel wrap(Child data) {",
        "return new ChildParcel(data);",
        "}",
        "public Child getContents() {",
        "return data;",
        "}",
        "@Override public int describeContents() {",
        "return 0;",
        "}",
        "@Override public void writeToParcel(Parcel dest, int flags) {",
        "Integer component1 = data.component1();",
        "dest.writeInt(component1);",
        "}",
        "}"
    ));

    assertAbout(javaSources()).that(asList(dataClassRoot, dataClassChild))
        .processedWith(new DataParcelProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(rootParcel, childParcel);
  }

  @Test public void listOfListOfDataTypesTest() throws Exception {
    JavaFileObject dataClassRoot = JavaFileObjects.forSourceString("test.Root", Joiner.on('\n').join(
        "package test;",
        "import nz.bradcampbell.dataparcel.DataParcel;",
        "import java.util.List;",
        "@DataParcel",
        "public final class Root {",
        "private final List<List<Child>> child;",
        "public Root(List<List<Child>> child) {",
        "this.child = child;",
        "}",
        "public List<List<Child>> component1() {",
        "return this.child;",
        "}",
        "}"
    ));

    JavaFileObject dataClassChild = JavaFileObjects.forSourceString("test.Child", Joiner.on('\n').join(
        "package test;",
        "public final class Child {",
        "private final Integer test;",
        "public Child(Integer test) {",
        "this.test = test;",
        "}",
        "public Integer component1() {",
        "return this.test;",
        "}",
        "}"
    ));

    JavaFileObject rootParcel = JavaFileObjects.forSourceString("test/RootParcel", Joiner.on('\n').join(
        "package test;",
        "import android.os.Parcel;",
        "import android.os.Parcelable;",
        "import java.lang.Override;",
        "import java.util.ArrayList;",
        "import java.util.List;",
        "public class RootParcel implements Parcelable {",
        "public static final Parcelable.Creator<RootParcel> CREATOR = new Parcelable.Creator<RootParcel>() {",
        "@Override public RootParcel createFromParcel(Parcel in) {",
        "return new RootParcel(in);",
        "}",
        "@Override public RootParcel[] newArray(int size) {",
        "return new RootParcel[size];",
        "}",
        "};",
        "private final Root data;",
        "private RootParcel(Root data) {",
        "this.data = data;",
        "}",
        "private RootParcel(Parcel in) {",
        "List<List<Child>> component1 = null;",
        "List<List<ChildParcel>> component1Wrapped = (List<List<ChildParcel>>) in.readArrayList(getClass().getClassLoader());",
        "component1 = new ArrayList<>(component1Wrapped.size());",
        "for (List<ChildParcel> _component1Wrapped : component1Wrapped) {",
        "List<Child> _component1 = null;",
        "_component1 = new ArrayList<>(_component1Wrapped.size());",
        "for (ChildParcel __component1Wrapped : _component1Wrapped) {",
        "Child __component1 = null;",
        "__component1 = __component1Wrapped.getContents();",
        "_component1.add(__component1);",
        "}",
        "component1.add(_component1);",
        "}",
        "this.data = new Root(component1);",
        "}",
        "public static final RootParcel wrap(Root data) {",
        "return new RootParcel(data);",
        "}",
        "public Root getContents() {",
        "return data;",
        "}",
        "@Override public int describeContents() {",
        "return 0;",
        "}",
        "@Override public void writeToParcel(Parcel dest, int flags) {",
        "List<List<Child>> component1 = data.component1();",
        "List<List<ChildParcel>> component1Wrapped = new ArrayList<>(component1.size());",
        "for (List<Child> component1Item : component1) {",
        "List<Child> _component1 = component1Item;",
        "List<ChildParcel> _component1Wrapped = new ArrayList<>(_component1.size());",
        "for (Child _component1Item : _component1) {",
        "ChildParcel __component1 = ChildParcel.wrap(_component1Item);",
        "_component1Wrapped.add(__component1);",
        "}",
        "component1Wrapped.add(_component1Wrapped);",
        "}",
        "dest.writeList(component1Wrapped);",
        "}",
        "}"
    ));

    JavaFileObject childParcel = JavaFileObjects.forSourceString("test/ChildParcel", Joiner.on('\n').join(
        "package test;",
        "import android.os.Parcel;",
        "import android.os.Parcelable;",
        "import java.lang.Integer;",
        "import java.lang.Override;",
        "public class ChildParcel implements Parcelable {",
        "public static final Parcelable.Creator<ChildParcel> CREATOR = new Parcelable.Creator<ChildParcel>() {",
        "@Override public ChildParcel createFromParcel(Parcel in) {",
        "return new ChildParcel(in);",
        "}",
        "@Override public ChildParcel[] newArray(int size) {",
        "return new ChildParcel[size];",
        "}",
        "};",
        "private final Child data;",
        "private ChildParcel(Child data) {",
        "this.data = data;",
        "}",
        "private ChildParcel(Parcel in) {",
        "Integer component1 = null;",
        "component1 = in.readInt();",
        "this.data = new Child(component1);",
        "}",
        "public static final ChildParcel wrap(Child data) {",
        "return new ChildParcel(data);",
        "}",
        "public Child getContents() {",
        "return data;",
        "}",
        "@Override public int describeContents() {",
        "return 0;",
        "}",
        "@Override public void writeToParcel(Parcel dest, int flags) {",
        "Integer component1 = data.component1();",
        "dest.writeInt(component1);",
        "}",
        "}"
    ));

    assertAbout(javaSources()).that(asList(dataClassRoot, dataClassChild))
        .processedWith(new DataParcelProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(rootParcel, childParcel);
  }

  @Test public void listOfParcelableMapsTest() throws Exception {
    JavaFileObject source = JavaFileObjects.forSourceString("test.Test", Joiner.on('\n').join(
        "package test;",
        "import nz.bradcampbell.dataparcel.DataParcel;",
        "import java.util.List;",
        "import java.util.Map;",
        "@DataParcel",
        "public final class Test {",
        "private final List<Map<Integer, Integer>> testList;",
        "public Test(List<Map<Integer, Integer>> testList) {",
        "this.testList = testList;",
        "}",
        "public List<Map<Integer, Integer>> component1() {",
        "return this.testList;",
        "}",
        "}"
    ));

    JavaFileObject expectedSource = JavaFileObjects.forSourceString("test/TestParcel", Joiner.on('\n').join(
        "package test;",
        "import android.os.Parcel;",
        "import android.os.Parcelable;",
        "import java.lang.Integer;",
        "import java.lang.Override;",
        "import java.util.List;",
        "import java.util.Map;",
        "public class TestParcel implements Parcelable {",
        "public static final Parcelable.Creator<TestParcel> CREATOR = new Parcelable.Creator<TestParcel>() {",
        "@Override public TestParcel createFromParcel(Parcel in) {",
        "return new TestParcel(in);",
        "}",
        "@Override public TestParcel[] newArray(int size) {",
        "return new TestParcel[size];",
        "}",
        "};",
        "private final Test data;",
        "private TestParcel(Test data) {",
        "this.data = data;",
        "}",
        "private TestParcel(Parcel in) {",
        "List<Map<Integer, Integer>> component1 = null;",
        "component1 = (List<Map<Integer, Integer>>) in.readArrayList(getClass().getClassLoader());",
        "this.data = new Test(component1);",
        "}",
        "public static final TestParcel wrap(Test data) {",
        "return new TestParcel(data);",
        "}",
        "public Test getContents() {",
        "return data;",
        "}",
        "@Override public int describeContents() {",
        "return 0;",
        "}",
        "@Override public void writeToParcel(Parcel dest, int flags) {",
        "List<Map<Integer, Integer>> component1 = data.component1();",
        "dest.writeList(component1);",
        "}",
        "}"
    ));

    assertAbout(javaSource()).that(source)
        .processedWith(new DataParcelProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(expectedSource);
  }

  @Test public void listOfNonParcelableMapsTest() throws Exception {
    JavaFileObject dataClassRoot = JavaFileObjects.forSourceString("test.Root", Joiner.on('\n').join(
        "package test;",
        "import nz.bradcampbell.dataparcel.DataParcel;",
        "import java.util.List;",
        "import java.util.Map;",
        "@DataParcel",
        "public final class Root {",
        "private final List<Map<Integer, Child>> child;",
        "public Root(List<Map<Integer, Child>> child) {",
        "this.child = child;",
        "}",
        "public List<Map<Integer, Child>> component1() {",
        "return this.child;",
        "}",
        "}"
    ));

    JavaFileObject dataClassChild = JavaFileObjects.forSourceString("test.Child", Joiner.on('\n').join(
        "package test;",
        "public final class Child {",
        "private final Integer test;",
        "public Child(Integer test) {",
        "this.test = test;",
        "}",
        "public Integer component1() {",
        "return this.test;",
        "}",
        "}"
    ));

    JavaFileObject rootParcel = JavaFileObjects.forSourceString("test/RootParcel", Joiner.on('\n').join(
        "package test;",
        "import android.os.Parcel;",
        "import android.os.Parcelable;",
        "import java.lang.Integer;",
        "import java.lang.Override;",
        "import java.util.ArrayList;",
        "import java.util.HashMap;",
        "import java.util.List;",
        "import java.util.Map;",
        "public class RootParcel implements Parcelable {",
        "public static final Parcelable.Creator<RootParcel> CREATOR = new Parcelable.Creator<RootParcel>() {",
        "@Override public RootParcel createFromParcel(Parcel in) {",
        "return new RootParcel(in);",
        "}",
        "@Override public RootParcel[] newArray(int size) {",
        "return new RootParcel[size];",
        "}",
        "};",
        "private final Root data;",
        "private RootParcel(Root data) {",
        "this.data = data;",
        "}",
        "private RootParcel(Parcel in) {",
        "List<Map<Integer, Child>> component1 = null;",
        "List<Map<Integer, ChildParcel>> component1Wrapped = (List<Map<Integer, ChildParcel>>) in.readArrayList(getClass().getClassLoader());",
        "component1 = new ArrayList<>(component1Wrapped.size());",
        "for (Map<Integer, ChildParcel> _component1Wrapped : component1Wrapped) {",
        "Map<Integer, Child> _component1 = null;",
        "_component1 = new HashMap<>(_component1Wrapped.size());",
        "for (Integer __component1Wrapped : _component1Wrapped.keySet()) {",
        "Integer __component1 = null;",
        "__component1 = __component1Wrapped;",
        "ChildParcel $_component1Wrapped = _component1Wrapped.get(__component1Wrapped);",
        "Child $_component1 = null;",
        "$_component1 = $_component1Wrapped.getContents();",
        "_component1.put(__component1, $_component1);",
        "}",
        "component1.add(_component1);",
        "}",
        "this.data = new Root(component1);",
        "}",
        "public static final RootParcel wrap(Root data) {",
        "return new RootParcel(data);",
        "}",
        "public Root getContents() {",
        "return data;",
        "}",
        "@Override public int describeContents() {",
        "return 0;",
        "}",
        "@Override public void writeToParcel(Parcel dest, int flags) {",
        "List<Map<Integer, Child>> component1 = data.component1();",
        "List<Map<Integer, ChildParcel>> component1Wrapped = new ArrayList<>(component1.size());",
        "for (Map<Integer, Child> component1Item : component1) {",
        "Map<Integer, Child> _component1 = component1Item;",
        "Map<Integer, ChildParcel> _component1Wrapped = new HashMap<>(_component1.size());",
        "for (Integer _component1Item : _component1.keySet()) {",
        "Integer __component1 = _component1Item;",
        "ChildParcel $_component1 = ChildParcel.wrap(_component1.get(_component1Item));",
        "_component1Wrapped.put(__component1, $_component1);",
        "}",
        "component1Wrapped.add(_component1Wrapped);",
        "}",
        "dest.writeList(component1Wrapped);",
        "}",
        "}"
    ));

    JavaFileObject childParcel = JavaFileObjects.forSourceString("test/ChildParcel", Joiner.on('\n').join(
        "package test;",
        "import android.os.Parcel;",
        "import android.os.Parcelable;",
        "import java.lang.Integer;",
        "import java.lang.Override;",
        "public class ChildParcel implements Parcelable {",
        "public static final Parcelable.Creator<ChildParcel> CREATOR = new Parcelable.Creator<ChildParcel>() {",
        "@Override public ChildParcel createFromParcel(Parcel in) {",
        "return new ChildParcel(in);",
        "}",
        "@Override public ChildParcel[] newArray(int size) {",
        "return new ChildParcel[size];",
        "}",
        "};",
        "private final Child data;",
        "private ChildParcel(Child data) {",
        "this.data = data;",
        "}",
        "private ChildParcel(Parcel in) {",
        "Integer component1 = null;",
        "component1 = in.readInt();",
        "this.data = new Child(component1);",
        "}",
        "public static final ChildParcel wrap(Child data) {",
        "return new ChildParcel(data);",
        "}",
        "public Child getContents() {",
        "return data;",
        "}",
        "@Override public int describeContents() {",
        "return 0;",
        "}",
        "@Override public void writeToParcel(Parcel dest, int flags) {",
        "Integer component1 = data.component1();",
        "dest.writeInt(component1);",
        "}",
        "}"
    ));

    assertAbout(javaSources()).that(asList(dataClassRoot, dataClassChild))
        .processedWith(new DataParcelProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(rootParcel, childParcel);
  }
}