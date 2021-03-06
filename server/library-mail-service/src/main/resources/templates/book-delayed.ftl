<!DOCTYPE html>
<html>
<head>
<style>
   .book-detail {
        border: 2px solid #ddd;
        padding: 10px;
        word-break: break-all;
    }

 hr  {
 border: 2px solid #f44336;
  border-radius: 3px;
 
 }


    .button {
        padding: 10px 28px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        margin: 4px 2px;
        transition-duration: 0.4s;
        cursor: pointer;
        background-color: #f44336;
        color: white;
        border: 2px solid #f44336;
        border-radius: 8px;
    }

 

    .avatar {
        vertical-align : middle;
        horizontal-align : middle;
        width: 150px;
        height: 150px;
        border-radius: 50%;
        border: solid #ddd;

    }
    
    
	.back {
    	 background-image: linear-gradient(to bottom right,#FFC967, rgb(255,48,76));
    
    }
</style>
</head>
<body>
   
    <table style="width : 100%; height : 100%"  class = "back">

        <tr>

            <td>
                <table
                     style="width : 70%; height :500px; text-align : center;font-family:'Open Sans', Arial, sans-serif;border : 2px solid #ddd; background-color : white; box-shadow: 5px 5px 5px #aaaaaa; margin-top : 30px; margin-bottom: 30px"
                    align="center">
                    <tr>
                        <td style="height: 100px; text-align : right;">

                            <table style="width : 100%">

                                <tr>
                                    <td>
                                        <h3 style="text-align : left; font-size : 26px;"> Open Book Library - Where you matter </h3>

                                    </td>

                                    <td>
                                        <img src="https://a.mailmunch.co/user_data/landing_pages/1522165168450-Book%20tablet%20icon.png"
                                            class="avatar">

                                    </td>

                                </tr>


                            </table>

                        </td>
                    </tr>

                    <tr>
                        <td style="text-align : left;">
<hr>
                            <h4 style="font-size : 21px;"> Hi, ${bookTransaction.borrowedToUser.userName} </h4>


                            <h4 style="color : red; font-size : 20px;"> The book is delayed for deposit, urgent action required </h4>
                            <table
                                style="width : 100%; height : 260px; table-layout: fixed; font-weight: bold; border-collapse: collapse;">
                                <tr style="height: 50px;">
                                    <td class="book-detail" style="width : 150px;" rowspan="5">
                                        <img src=${bookTransaction.borrowedBook.bookImages[0]}
                                            style=" height : 230px; width : 150px">
                                    </td>
                                    <td class="book-detail">
                                        Title
                                    </td>
                                    <td class="book-detail">
                                        ${bookTransaction.borrowedBook.bookTitle}
                                    </td>
                                </tr>
                                <tr style="height: 50px;">
                                    <td class="book-detail">
                                        Author
                                    </td>
                                    <td class="book-detail">
                                        ${bookTransaction.borrowedBook.bookAuthor.authorName}
                                    </td>
                                </tr>
                                <tr style="height: 50px;">
                                    <td class="book-detail">
                                        Borrow Date
                                    </td>
                                    <td class="book-detail">
                                        ${bookTransaction.borrowedDate}
                                    </td>
                                </tr>

                                <tr style="height: 50px;">
                                    <td class="book-detail">
                                        Return Date
                                    </td>
                                    <td class="book-detail">
                                        ${bookTransaction.returnDate}
                                    </td>
                                </tr>


                                <tr style="height: 50px;">
                                    <td class="book-detail">
                                        Deposited Fine
                                    </td>
                                    <td class="book-detail">
                                        ${bookTransaction.fineTillDate}
                                    </td>
                                </tr>





                            </table>
                            <a class="button" style="display:block; margin :30px auto; width : 120px;" href=${link}>
                                Know More</a>

                        </td>

                    </tr>


                    <tr>
                        <td align="center" valign="top" bgcolor="#30373b" class="em_aside">
                            <table width="600" cellpadding="0" cellspacing="0" border="0" align="center"
                                class="em_main_table" style="table-layout:fixed;">
                                <tr>
                                    <td height="35" class="em_height">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td valign="top" align="center">
                                        <table border="0" cellspacing="0" cellpadding="0" align="center">
                                            <tr>
                                                <td valign="top"><a href="#" target="_blank"
                                                        style="text-decoration:none;"><img
                                                            src="https://www.sendwithus.com/assets/img/emailmonks/images/fb.png"
                                                            width="26" height="26"
                                                            style="display:block;font-family: Arial, sans-serif; font-size:10px; line-height:18px; color:#feae39; "
                                                            border="0" alt="Fb" /></a></td>
                                                <td width="7">&nbsp;</td>
                                                <td valign="top"><a href="#" target="_blank"
                                                        style="text-decoration:none;"><img
                                                            src="https://www.sendwithus.com/assets/img/emailmonks/images/tw.png"
                                                            width="26" height="26"
                                                            style="display:block;font-family: Arial, sans-serif; font-size:10px; line-height:18px; color:#feae39; "
                                                            border="0" alt="Tw" /></a></td>
                                                <td width="7">&nbsp;</td>
                                                <td valign="top"><a href="#" target="_blank"
                                                        style="text-decoration:none;"><img
                                                            src="https://www.sendwithus.com/assets/img/emailmonks/images/pint.png"
                                                            width="26" height="26"
                                                            style="display:block;font-family: Arial, sans-serif; font-size:10px; line-height:18px; color:#feae39; "
                                                            border="0" alt="pint" /></a></td>
                                                <td width="7">&nbsp;</td>
                                                <td valign="top"><a href="#" target="_blank"
                                                        style="text-decoration:none;"><img
                                                            src="https://www.sendwithus.com/assets/img/emailmonks/images/google.png"
                                                            width="26" height="26"
                                                            style="display:block;font-family: Arial, sans-serif; font-size:10px; line-height:18px; color:#feae39; "
                                                            border="0" alt="G+" /></a></td>
                                                <td width="7">&nbsp;</td>
                                                <td valign="top"><a href="#" target="_blank"
                                                        style="text-decoration:none;"><img
                                                            src="https://www.sendwithus.com/assets/img/emailmonks/images/insta.png"
                                                            width="26" height="26"
                                                            style="display:block;font-family: Arial, sans-serif; font-size:10px; line-height:18px; color:#feae39; "
                                                            border="0" alt="Insta" /></a></td>
                                                <td width="7">&nbsp;</td>
                                                <td valign="top"><a href="#" target="_blank"
                                                        style="text-decoration:none;"><img
                                                            src="https://www.sendwithus.com/assets/img/emailmonks/images/yt.png"
                                                            width="26" height="26"
                                                            style="display:block;font-family: Arial, sans-serif; font-size:10px; line-height:18px; color:#feae39; "
                                                            border="0" alt="Yt" /></a></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td height="22" class="em_height">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td align="center"
                                        style="font-family:'Open Sans', Arial, sans-serif; font-size:12px; line-height:18px; color:#848789; text-transform:uppercase;">
                                        <span style="text-decoration:underline;"><a href="#" target="_blank"
                                                style="text-decoration:underline; color:#848789;">PRIVACY
                                                STATEMENT</a></span> &nbsp;&nbsp;|&nbsp;&nbsp; <span
                                            style="text-decoration:underline;"><a href="#" target="_blank"
                                                style="text-decoration:underline; color:#848789;">TERMS OF
                                                SERVICE</a></span><span class="em_hide"> &nbsp;&nbsp;|&nbsp;&nbsp;
                                        </span><span class="em_br"></span><span style="text-decoration:underline;"><a
                                                href="#" target="_blank"
                                                style="text-decoration:underline; color:#848789;">RETURNS</a></span>
                                    </td>
                                </tr>
                                <tr>
                                    <td height="10" style="font-size:1px; line-height:1px;">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td align="center"
                                        style="font-family:'Open Sans', Arial, sans-serif; font-size:12px; line-height:18px; color:#848789;text-transform:uppercase;">
                                        &copy;2&zwnj;020 company name. All Rights Reserved.
                                    </td>
                                </tr>
                                <tr>
                                    <td height="10" style="font-size:1px; line-height:1px;">&nbsp;</td>
                                </tr>

                                <tr>
                                    <td height="35" class="em_height">&nbsp;</td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>

            </td>
        </tr>
    </table>
</body>

</html>