import { Role } from './role';

export class User {
    userId : number;
    userEmail : string;
    userName : string;
	userPhoneNo : string;
    userAddress : string;
    password : string;
    isEnabled : boolean;
    roles : Role[];
   
   
     constructor(userId: number,userEmail: string, userName: string, userPhoneNo: string,userAddress : string,password : string, roles : Role[]) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPhoneNo = userPhoneNo;
        this.userAddress = userAddress;
        this.password = password;
        this.roles = roles;
      
    }
}