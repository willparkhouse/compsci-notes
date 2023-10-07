A K-Dimensional (KD Tree) is a binary search tree where data in each node is a K-Dimensional **point in space**. A KD tree is useful for **space partitioning**.
A **non-leaf** node divides the space into two parts, called half-spaces. The 'left' side of this split is in the left subtree, and vice versa.
For a 2D KD tree, each layer in the tree alternates between an X-aligned plane and a Y-aligned plane. If we number the planes 0, 1, 2, 3,... , (K-1). 
A node at depth D will have an A-aligned plane, where A is calculated as: A = D mod K
