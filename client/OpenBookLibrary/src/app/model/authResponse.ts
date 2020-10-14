import { Role } from './role';

export class AuthResponse { 
    accessToken : string;
    loggedInUserId : number;
    loggedInUserEmail : string;
    loggedInUserRoles : Set<Role>;
}