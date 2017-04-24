package MatrixUtil;

/**
 * This class are used to store the Matrix and giving them some utility to play
 * with matrix that Ko Robyn have teach us.
 * 
 * Some utility that can be used to play with: - Transpose - Find the cofactor
 * matrix - Find the determiner - Multiplication
 * 
 * @version v0.1.1-alpha1
 * @author Chris(2016730011)
 * @author Irvan(2016730077)
 *
 */
public class Matrix {
	/**
	 * Matrix storage system, use 2d array.
	 */
	private double[][] matrix;

	/**
	 * Just a prettify settings how significant the digit must be.
	 */
	public static int significantDigit = 2;

	/**
	 * Instantiate blank matrix
	 * 
	 * @param rowSize
	 *            n of the Matrix.
	 * @param colSize
	 *            m of the Matrix.
	 */
	public Matrix(int rowSize, int colSize) {
		matrix = new double[rowSize][colSize];
	}

	/**
	 * Instantiate matrix from 2d array
	 * 
	 * @param matrix
	 *            existing 2d array of matrix.
	 */
	public Matrix(double[][] matrix) {
		this.matrix = matrix;
	}

	/**
	 * Convert current matrix as array of integer
	 * 
	 * @return Matrix on 2D array.
	 */
	public double[][] toIntArray() {
		return matrix;
	}

	/**
	 * Changes the current value on a particular position
	 * 
	 * @param row
	 *            row position.
	 * @param col
	 *            column position.
	 * @param value
	 *            value that want to be changed.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             when the position are not in bounds.
	 */
	public void setValue(int row, int col, double value) throws IndexOutOfBoundsException {
		matrix[row][col] = value;
	}

	
	
	
	
	
	/*************** BASIC MATH REGION ***************/

	/**
	 * Multiply this matrix with a scalar of something.
	 * 
	 * @param skalar
	 */
	public void multiply(double skalar) {
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[0].length; j++)
				matrix[i][j] *= skalar;
	}

	/**
	 * Add this matrix with a scalar of something.
	 * 
	 * @param skalar
	 */
	public void add(double skalar) {
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[0].length; j++)
				matrix[i][j] += skalar;
	}

	/**
	 * Substract this matrix with a scalar of something.
	 * 
	 * @param skalar
	 */
	public void substract(double skalar) {
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[0].length; j++)
				matrix[i][j] -= skalar;
	}

	/**
	 * Divide this matrix with a scalar of something.
	 * 
	 * @param skalar
	 */
	public void divide(double skalar) {
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[0].length; j++)
				matrix[i][j] /= skalar;
	}
	
	
	
	
	

	/*************** SEMIBASIC MATH REGION ***************/
	// @TODO add `add` operation with matrix
	// @TODO add `multiply` operation with matrix
	// @TODO add `division` operation with matrix

	
	
	
	
	
	/*************** MATRIX ONLY MATH REGION ***************/

	/**
	 * Get a submatrix version of the matrix. This will return new matrix
	 * instance apart from this object itself.
	 * 
	 * @param n
	 *            row should be avoided
	 * @param m
	 *            column should be avoided
	 * @return
	 */
	private Matrix getSubmatrix(int m, int n) {
		if (matrix.length < 3 || matrix[0].length < 3)
			return null;

		Matrix x = new Matrix(matrix.length - 1, matrix[0].length - 1);

		for (int i = 0, row = 0; i < matrix.length; i++) {
			if (i == m)
				continue;
			for (int j = 0, col = 0; j < matrix[0].length; j++) {
				if (j == n)
					continue;
				x.setValue(row, col++, matrix[i][j]);
			}
			row++;
		}

		return x;
	}

	/**
	 * Get the cofactor matrix of this matrix.
	 * 
	 * @return Cofactor Matrix.
	 * @throws IndexOutOfBoundsException
	 * @throws InvalidMoveException
	 */
	public Matrix getCofactor() throws IndexOutOfBoundsException, InvalidMoveException {
		Matrix x = new Matrix(matrix.length, matrix[0].length);
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix.length; j++)
				x.setValue(i, j, ((i + j) % 2 == 0 ? 1 : -1) * getSubmatrix(i, j).getDeterminant());
		return x;
	}

	/**
	 * Get the determinant of this matrix.
	 * 
	 * @return determinant
	 * @throws InvalidMoveException
	 */
	public double getDeterminant() throws InvalidMoveException {
		if (matrix.length != matrix[0].length)
			throw new InvalidMoveException("Unable to calculate the determinant, it's not n x n matix.");

		if (matrix.length == 2 && matrix[0].length == 2)
			return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

		int temp = 0;
		for (int i = 0; i < matrix.length; i++)
			temp += (i % 2 == 1 ? -1 : 1) * matrix[i][0] * getSubmatrix(i, 0).getDeterminant();

		return temp;
	}

	/**
	 * Transposing this matrix.
	 * 
	 * @return transposed Matrix.
	 */
	public void transpose() {
		this.matrix = getTranspose().toIntArray();
	}

	/**
	 * Transposing a cloned version of this matrix.
	 * 
	 * @return
	 */
	public Matrix getTranspose() {
		double[][] jadi = new double[matrix[0].length][matrix.length];
		for (int i = 0; i < jadi.length; i++)
			for (int j = 0; j < jadi[0].length; j++)
				jadi[i][j] = matrix[j][i];
		return new Matrix(jadi);
	}

	/**
	 * Inverse this matrix.
	 * 
	 * @return
	 * @throws InvalidMoveException
	 */
	public void inverse() throws InvalidMoveException {
		this.matrix = getInverse().toIntArray();
	}

	/**
	 * Inverse the cloned version of this matrix.
	 * 
	 * @return
	 * @throws InvalidMoveException
	 */
	public Matrix getInverse() throws InvalidMoveException {
		Matrix temp = null;

		// getting the cofactor matrix
		try {
			temp = this.getCofactor();
		} catch (IndexOutOfBoundsException e) {
			// actually this will never happened when we're the one who
			// handles the code.
		}

		// transpose it.
		temp.transpose();

		// multiply it with the determinant.
		temp.multiply(1 / this.getDeterminant());

		return temp;
	}

	
	
	
	
	
	/*************** JAVA OBJECT SPECIFICATION REGION ***************/
	/**
	 * Representate this matrix into an string.
	 * 
	 * @return matrix in well-formatted string.
	 */
	@Override
	public String toString() {
		String temp = "";

		// item statement are used to generate format for the matrix.
		String itemStatement = " %x.yf"
				.replace("x", "" + (Prettify.countSingleDigitSpace(this) + Matrix.significantDigit + 2))
				.replace("y", "" + Matrix.significantDigit);

		for (double[] row : matrix) {
			for (double col : row)
				temp += String.format(itemStatement, col);
			temp += "\n";
		}

		// this one are used to clean up those junks.
		// if you need to know it's function, you can disable it,
		// it's just kind of magic, here :D
		return temp.replaceAll("[\\s\\n]+$", "");
	}

	
	
	
	
	
	/*************** STATIC METHOD REGION ***************/

	/**
	 * Generate an identity matrix
	 * 
	 * @param size
	 *            desired size of the matrix
	 * @return identity matrix, n x n
	 */
	public static Matrix getIdentityMatrix(int size) {
		Matrix m = new Matrix(size, size);

		for (int i = 0; i < size; i++)
			m.setValue(i, i, 1);

		return m;
	}
}
