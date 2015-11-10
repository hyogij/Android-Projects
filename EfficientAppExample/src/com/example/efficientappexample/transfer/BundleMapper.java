package com.example.efficientappexample.transfer;

import java.lang.reflect.Field;
import java.util.Set;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class BundleMapper {
	private static final String CLASS_NAME = BundleMapper.class
			.getCanonicalName();

	public static boolean toBundle(Object container, Bundle bundle) {
		Field[] fields = container.getClass().getFields();
		String name;
		
		Log.d(CLASS_NAME, "toBundle " + bundle.toString());
		for (Field f : fields) {
			Log.d(CLASS_NAME, "Field " + f.toString());
			
			BundleField ref = f.getAnnotation(BundleField.class);
			String sname = f.getType().getSimpleName();
			if (ref != null) {
				name = ref.name();
			} else {
				name = f.getName();
				continue;
			}

			try {
				if (sname.equals("String") == true) {
					bundle.putString(name, (String) f.get(container));
				} else if (sname.equals("int") == true) {
					bundle.putInt(name, f.getInt(container));
				} else if (sname.equals("long") == true) {
					bundle.putLong(name, f.getLong(container));
				} else if (sname.equals("float") == true) {
					bundle.putFloat(name, f.getFloat(container));
				} else if (sname.equals("double") == true) {
					bundle.putDouble(name, f.getDouble(container));
				}
			} catch (Exception e) {
				Log.d(CLASS_NAME, name + " " + e.getMessage());
			}
		}
		return false;
	}

	private static boolean fromBundle(Object container, Bundle bundle) {
		if (bundle == null) {
			return false;
		}

		Set<String> keys = bundle.keySet();
		Class<?> cls = container.getClass();
		for (String key : keys) {
			try {
				Field field = cls.getField(key);
				field.setAccessible(true);
				BundleField ref = field.getAnnotation(BundleField.class);
				if (ref != null) {
					if (cls.isPrimitive() == true) {
						String sname = field.getType().getSimpleName();
						if (sname.equals("int") == true) {
							field.setInt(container, bundle.getInt(key));
						} else if (sname.equals("float") == true) {
							field.setFloat(container, bundle.getFloat(key));
						} else {
							return false;
						}
						return true;
					} else {
						Object value = bundle.get(key);
						field.set(container, value);
					}
				}
			} catch (NoSuchFieldException e) {
				Log.d(CLASS_NAME, "NoSuchFieldException " + e.getMessage());
			} catch (Exception e) {
				Log.d(CLASS_NAME, "Exception " + e.getMessage());
			}
		}

		return false;
	}

	public static boolean fromIntent(Object container, Intent intent) {
		// Uri uri = getData();
		if (intent == null) {
			return false;
		}

		return fromBundle(container, intent.getExtras());
	}
}