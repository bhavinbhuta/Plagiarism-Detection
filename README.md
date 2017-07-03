# Plagiarism-Detection
Java implementation of detecting plagiarism for images

The motivation behind designing such a mechanism is to identify plagiarism acts for images. The flow of the system goes like this an input query image is checked against a database of images(not literally explained later) and if a match is found from database then the image is said to be plagiarised else not. As we need to identify images there's no point is using cryptographic hash as cryptographic hash identify as either black or white. Hence, we made use of Perceptual Hash Algorithm which constructs hash for images based on its features and similar images have similar hash code. Hamming Distance was made use of for calculating the difference between hash generated for input image with all stored ones in database. A threshold was identified which was used as benchmark for future checks.
One more feature that was taken care of was rotation of images, i.e. each image was rotated by 15 degree and newly created 24 different hash values were stored in the database. 

Programming Language Used:- Java, SQL.
Tool Used:- Eclipse, MySQL. 
