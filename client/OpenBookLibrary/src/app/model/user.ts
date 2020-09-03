
export class User {
    userId : number;
    userEmail : string;
    userName : string;
	userPhoneNo : string;
    userAddress : string;
    isAdmin : boolean;
    hashedPassword : string;
    
    constructor(userId: number,userEmail: string, userName: string, userPhoneNo: string,userAddress : string, isAdmin: boolean,hashedPassword : string) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPhoneNo = userPhoneNo;
        this.userAddress = userAddress;
        this.isAdmin = isAdmin;
        this.hashedPassword = hashedPassword;
        
    }
}