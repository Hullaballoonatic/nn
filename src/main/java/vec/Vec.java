package vec;// ----------------------------------------------------------------
// The contents of this file are distributed under the CC0 license.
// See http://creativecommons.org/publicdomain/zero/1.0/
// ----------------------------------------------------------------

import helpers.Json;

/// Represents a vector of doubles
public class Vec {
    public double[] data;
    private int start;
    private int len;

    /// Makes an vector of the specified size
    Vec(int size) {
        if (size == 0)
            data = null;
        else
            data = new double[size];
        start = 0;
        len = size;
    }

    /// Wraps the specified array of doubles
    public Vec(double[] data) {
        this.data = data;
        start = 0;
        len = data.length;
    }

    /// This is NOT a copy constructor. It wraps the same buffer of values as v.
    Vec(Vec v, int begin, int length) {
        data = v.data;
        start = v.start + begin;
        len = length;
    }

    /// Unmarshalling constructor
    public Vec(Json n) {
        data = new double[n.size()];
        for (int i = 0; i < n.size(); i++)
            data[i] = n.getDouble(i);
        start = 0;
        len = n.size();
    }

    public Json marshal() {
        Json list = Json.newList();
        for (int i = 0; i < len; i++)
            list.add(data[start + i]);
        return list;
    }

    public int size() {
        return len;
    }

    public double get(int index) {
        return data[start + index];
    }

    public void set(int index, double value) {
        data[start + index] = value;
    }

    private void fill(double v) {
        for (int i = 0; i < len; i++)
            data[start + i] = v;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (len > 0) {
            sb.append(data[start]);
            for (int i = 1; i < len; i++) {
                sb.append(",");
                sb.append(data[start + i]);
            }
        }
        return sb.toString();
    }

    public double squaredMagnitude() {
        double d = 0.0;
        for (int i = 0; i < len; i++)
            d += data[start + i] * data[start + i];
        return d;
    }

    public void normalize() {
        double mag = squaredMagnitude();
        if (mag <= 0.0) {
            fill(0.0);
            data[0] = 1.0;
        } else {
            double s = 1.0 / Math.sqrt(mag);
            for (int i = 0; i < len; i++)
                data[i] *= s;
        }
    }

    public void copy(Vec that) {
        data = new double[that.size()];
        for (int i = 0; i < that.size(); i++)
            data[i] = that.get(i);
        start = 0;
        len = that.size();
    }

    public void add(Vec that) {
        if (that.size() != this.size())
            throw new IllegalArgumentException("mismatching sizes");
        for (int i = 0; i < len; i++)
            data[start + i] += that.get(i);
    }

    public void scale(double scalar) {
        for (int i = 0; i < len; i++)
            data[start + i] *= scalar;
    }

    public void addScaled(double scalar, Vec that) {
        if (that.size() != this.size())
            throw new IllegalArgumentException("mismatching sizes");
        for (int i = 0; i < len; i++)
            data[start + i] += scalar * that.get(i);
    }

    public double dotProduct(Vec that) {
        if (that.size() != this.size())
            throw new IllegalArgumentException("mismatching sizes");
        double d = 0.0;
        for (int i = 0; i < len; i++)
            d += get(i) * that.get(i);
        return d;
    }

    double squaredDistance(Vec that) {
        if (that.size() != this.size())
            throw new IllegalArgumentException("mismatching sizes");
        double d = 0.0;
        for (int i = 0; i < len; i++) {
            double t = get(i) - that.get(i);
            d += (t * t);
        }
        return d;
    }
}






