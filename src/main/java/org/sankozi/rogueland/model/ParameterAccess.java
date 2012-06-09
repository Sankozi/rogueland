package org.sankozi.rogueland.model;

/**
 * Access for single parameter value, parameter access is always done is some 
 * context 
 * @author sankozi
 */
public interface ParameterAccess {

	/**
	 * Returns codename of parameter
	 * @return 
	 */
	public String cn();
	/**
	 * Returns name (string readable for user) of parameter
	 * @return 
	 */
	public String name();

	/**
	 * Returns value of parameter
	 * @return 
	 */
	public float get();

	/**
	 * Sets change done to parameter in this context, subsequent calls will not
	 * sum but will overwrite 
	 * <pre>
	 * {@code
	 * pa.change(1f);
	 * pa.change(2f);
	 * //is equal to
	 * pa.change(2f);
	 * }
	 * </pre>
	 * @param d value that is added to value of parameter
	 */
	public void setChange(float d);
}
