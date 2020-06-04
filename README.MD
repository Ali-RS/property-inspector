### Property Inspector

 [ ![Download](https://api.bintray.com/packages/jayfella/com.jayfella/property-inspector/images/download.svg) ](https://bintray.com/jayfella/com.jayfella/property-inspector/_latestVersion) 

```java
dependencies() {
    implementation "com.jayfella:property-inspector:1.0.0"
}
```

Allows the editing of any Object properties via jMonkey Scene Objects called JmeComponents.
properties must have a JmeComponent associated with it to be able to edit it.

Built-In JmeComponents:
- Boolean
- ColorRGBA
- Enum
- Float
- Quaternion
- String
- Vector2f
- Vector3f
- Vector4f

For example, to map a Vector3f object to a JmeComponent:
```java
PropertyRegistrationService.initialize();
PropertyRegistrationService.getInstance().registerComponent(Vector3f.class, Vector3fComponent.class);
``` 

```java

ComponentBuilder builder;

// searches getters and setters within the class (must be one of each).
builder = new ReflectedComponentBuilder();
// or
// searches fields of the class.
builder = new ReflectedFieldComponentBuilder();

builder.setObject(myObject);
List<JmeComponent> components = builder.build();

Node componentNode = new Node("Component Node");

for (JmeComponent component : components) {
    componentNode.add(component.getPanel());
}

```