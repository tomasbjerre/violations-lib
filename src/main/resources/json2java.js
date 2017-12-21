toJava = function(o) {
  return o == null ? null : o.toJava();
};
Object.prototype.toJava = function() {
  var m = new java.util.HashMap();
  for (var key in this)
    if (this.hasOwnProperty(key))
      m.put(key, toJava(this[key]));
  return m;
};
Array.prototype.toJava = function() {
  var l = this.length;
  var a = new java.lang.reflect.Array.newInstance(java.lang.Object, l);
  for (var i = 0;i < l;i++)
    a[i] = toJava(this[i]);
  return a;
};
String.prototype.toJava = function() {
  return new java.lang.String(this);
};
Boolean.prototype.toJava = function() {
  return java.lang.Boolean.valueOf(this);
};
Number.prototype.toJava = function() {
  return java.lang.Integer(this);
};
