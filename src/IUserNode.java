public interface IUserNode {

    /**
     * @return the username of current user
     */
    String getName();

    /**
     * @return the user id of current user
     */
    int getID();

    /**
     * Set the username with given name
     *
     * @param name given username
     */
    void setName(String name);

    /**
     * Set the user id with given id
     *
     * @param id given user id
     */
    void setID(int id);

    /**
     * Use to print out username and id info
     */
    void printUser();

}
