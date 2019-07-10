package nn

import matrix.Matrix
import vector.Vector

interface Trainer : (Matrix, Matrix) -> Vector
