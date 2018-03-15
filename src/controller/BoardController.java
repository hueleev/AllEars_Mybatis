package controller;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sist.msk.Action;

import Userlist.EtcinfoDBBean;
import Userlist.EtcinfoDBMybatis;
import Userlist.EtcinfoDataBean;
import Userlist.UserlistDBBean;
import Userlist.UserlistDBMybatis;
import Userlist.UserlistDataBean;
import guestMsg.GuestDBBean;
import guestMsg.GuestDataBean;
import songBoard.SongDBBean;
import songBoard.SongDBMybatis;
import songBoard.SongDataBean;






	public class BoardController extends Action {

		public String index(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 
				 return  "/view/index.jsp"; 
				}
		
		public String intro(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 

				 return  "/user/intro.jsp"; 
				}
		public String joinForm(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable {
		
			int num=0;
			 if(req.getParameter("num")!=null){
				 num=Integer.parseInt(req.getParameter("num"));
			 };
			req.setAttribute("num",num);
			
		
				 return  "/user/joinForm.jsp"; 
				}
		
		
		
		public String joinPro(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable {
			
				UserlistDataBean user=new UserlistDataBean();
				
				if (req.getParameter("num")!=null&&!req.getParameter("num").equals("")) {
				user.setNum(Integer.parseInt(req.getParameter("num")));}

				user.setUserid(req.getParameter("userid"));
				user.setPasswd(req.getParameter("passwd"));
				user.setUsername(req.getParameter("username"));
				user.setDisplayname(req.getParameter("displayname"));
				user.setPosition(req.getParameter("position"));
				user.setGender(req.getParameter("gender"));
				user.setHp(req.getParameter("hp"));
				user.setAddress(req.getParameter("address"));
				user.setEmail(req.getParameter("email"));
				user.setBio(req.getParameter("bio"));


				
				System.out.println(user);
				UserlistDBMybatis dbPro = UserlistDBMybatis.getInstance(); 

				String pageNum=req.getParameter("pageNum");
				if(pageNum == null || pageNum == ""){
					pageNum ="1";}
	
				dbPro.insertUser(user);
			
	
	
				res.sendRedirect("intro");
	
	
				
				return null;
		}
		
		
		public String confirmId(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 
			
			 String userid = req.getParameter("userid");
			 UserlistDBMybatis dao = UserlistDBMybatis.getInstance();
			 boolean result = dao.confirmId(userid);
			 String none=null;
			 
			 req.setAttribute("result", result);
			 req.setAttribute("userid", userid);
				 return  "/confirmId.jsp"; 
				} 
		
		
		
		public String loginForm(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 
				 return  "/user/loginForm.jsp;"; 
				} 
		
		public String loginpro(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 
			
			 String userid=req.getParameter("userid");
			 String passwd=req.getParameter("passwd");
			 UserlistDBMybatis dao = UserlistDBMybatis.getInstance();
			 
			 int pwcheck = dao.login(userid, passwd);
			 
			 req.setAttribute("pwcheck", pwcheck);
			 if(pwcheck!=1) {
				
				 return "/user/iderror.jsp";				
			 }
			 
			 
			 
			 HttpSession session = req.getSession();
			 
			 UserlistDataBean user = dao.getUser2(userid,passwd);
			 String displayname=user.getDisplayname();
				session.setAttribute("sessionid",userid);
				session.setAttribute("sessionpasswd",passwd);
				session.setAttribute("sessiondisplayname",displayname);

				req.setAttribute("user", user);
				
				res.sendRedirect("main");
				
			return  null; 
			} 
		
		
		
		public String main(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable {
			
		
			 
			 HttpSession session = req.getSession();
			 String userid=(String) session.getAttribute("sessionid");
			 String passwd=(String) session.getAttribute("sessionpasswd");
			 
			 UserlistDBMybatis dao = UserlistDBMybatis.getInstance();
			 UserlistDataBean user=dao.getUser2(userid,passwd);
			 
			 EtcinfoDBMybatis dao2=EtcinfoDBMybatis.getInstance();
			 EtcinfoDataBean etc=dao2.getEtc(userid);
			
			 SongDBMybatis dao3 = SongDBMybatis.getInstance();
			 
			 
			 int follow=dao.followCount(userid);
			 int follower=dao.followerCount(userid);
			 List followList=dao.followList(userid);
			 List followerList=dao.followerList(userid);
			 List timelineList=dao2.getTimeline(userid);

			 
			 if(session.getAttribute("sessionid")==null)
			 {
				return "intro";
			 };
			 
			 	
			 req.setAttribute("followList", followList);
			 req.setAttribute("user", user);
			 req.setAttribute("etc", etc);
			 req.setAttribute("follow", follow);
			 req.setAttribute("follower", follower);
			 req.setAttribute("followerList", followerList);
			 req.setAttribute("timelineList", timelineList);
			
			 
				 return  "/view/main.jsp"; 
				}
		
		public String logoutPro(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 
			
			HttpSession session = req.getSession();
			session.invalidate();
			
			
			return  "intro"; 
			} 
		
		public String list(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 

			int pageSize = 5;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String pageNum = req.getParameter("pageNum");
			if (pageNum==null || pageNum == "") {
				pageNum = "1";}
			int currentPage = Integer.parseInt(pageNum);
			int startRow = (currentPage -1) * pageSize +1;
			int endRow = currentPage * pageSize;
			int count = 0;
			int number = 0;
			List userList = null;
			UserlistDBMybatis dbPro = UserlistDBMybatis.getInstance();
			count = dbPro.getUserCount();
			if (count >0){
				userList = dbPro.getUsers(startRow,endRow);}
				number = count - (currentPage - 1)*pageSize;
			
			int bottomLine = 5;
			int pageCount =count/pageSize
					+(count % pageSize == 0 ? 0 : 1);
			int startPage = 1 + (currentPage -1) / bottomLine * bottomLine;
			int endPage = startPage + bottomLine -1;
			if (endPage > pageCount) endPage = pageCount;
			
			req.setAttribute("userList",userList);
			req.setAttribute("number",number);
			req.setAttribute("count", count);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("startPage", startPage);
			req.setAttribute("bottomLine", bottomLine);
			req.setAttribute("pageCount", pageCount);
			req.setAttribute("endPage", endPage);
			
				 return  "/user/list.jsp";
				 
				} 
		
		public String content(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable {
		
		 String number = req.getParameter("number");
		
		 int num = Integer.parseInt(req.getParameter("num"));
		 
		 String pageNum = req.getParameter("pageNum");
		if (pageNum == null || pageNum == "") {
				pageNum = "1";	}
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		 try { 
			UserlistDBBean dbPro = UserlistDBBean.getInstance();
		 	UserlistDataBean user = dbPro.getUser(num,"content"); 
		 
		 	req.setAttribute("user", user); 	
			req.setAttribute("pageNum", pageNum);
	
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		
		 return  "/user/content.jsp"; 
		 }
		
		public String deleteForm(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 
				 
			
			int num=Integer.parseInt(req.getParameter("num"));
			String pageNum = req.getParameter("pageNum");
			
			UserlistDBBean dbPro = UserlistDBBean.getInstance();
		 	UserlistDataBean user = dbPro.getUser(num,"content"); 
			
		 	req.setAttribute("num", num);
		 	req.setAttribute("pageNum", pageNum);
		 	req.setAttribute("user", user);
		
		
			return  "/user/deleteForm.jsp"; 
				}
		
		public String deletePro(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 
			
			
			 String pageNum = req.getParameter("pageNum");
			 if(pageNum == null || pageNum == "") {
				 pageNum = "1";
			 }
			 
			 
			 int num = Integer.parseInt(req.getParameter("num"));
			 String userid=req.getParameter("userid");
			 String passwd = req.getParameter("passwd");
			 UserlistDBBean dbPro = UserlistDBBean.getInstance();

			 int check = -1;
	 
			 
			
			
			HttpSession session = req.getSession();
		 
			if (passwd.equals(session.getAttribute("sessionpasswd"))) {
				if (session.getAttribute("sessionid").equals("admin")) {
					
					int x=dbPro.deleteUser2(userid, "Admin");
					
					if (x==1) {
					check=0;

					}

					req.setAttribute("pageNum", pageNum);
					req.setAttribute("check",check);
				return  "/user/deletePro.jsp"; 
				
				}else {
					check=dbPro.deleteUser(userid,passwd);
					session.invalidate();
					req.setAttribute("check", check);
					return  "/user/deletePro.jsp"; 

				}

			}else {
				check=-1;
			}
			
			req.setAttribute("pageNum", pageNum);
			req.setAttribute("check",check);
			
			return "/user/deletePro.jsp";
	
		}
		
		public String updateForm1(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 
			
			
			UserlistDBBean dbPro = UserlistDBBean.getInstance();
			
			
			
			return  "/user/updateForm1.jsp"; 
				}
		
		
		public String updatePro1(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable {
			
			String passwd = req.getParameter("passwd");
			
			HttpSession session = req.getSession();
			
			if(session.getAttribute("sessionpasswd").equals(passwd)) {
				
				return "updateForm";
			}
			
			return  "/user/updatePro1.jsp"; 
				}
		
		
		public String updateForm(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable {
			
			HttpSession session = req.getSession();

			
			 String userid = (String)session.getAttribute("sessionid");
			 String passwd = (String)session.getAttribute("sessionpasswd");
	

			 try{
				 UserlistDBBean dbPro = UserlistDBBean.getInstance();
				 UserlistDataBean user = dbPro.getUser2(userid,passwd);

				 req.setAttribute("user", user);
				
			
				}catch (Exception e) {
					e.printStackTrace();
				}
			 return  "/user/updateForm.jsp"; 
		}
		
		public String updatePro(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable {
			
			
			
			UserlistDataBean user=new UserlistDataBean();
			
			if (req.getParameter("num")!=null&&!req.getParameter("num").equals("")) {
				user.setNum(Integer.parseInt(req.getParameter("num")));}

					
			user.setPasswd(req.getParameter("passwd"));
			user.setDisplayname(req.getParameter("displayname"));
			user.setPosition(req.getParameter("position"));
			user.setHp(req.getParameter("hp"));
			user.setAddress(req.getParameter("address"));
			user.setEmail(req.getParameter("email"));
			user.setBio(req.getParameter("bio"));

			
			
			System.out.println(user);
			UserlistDBBean dbPro = UserlistDBBean.getInstance();
			int chk=dbPro.updateUser(user); 

			req.setAttribute("user", user);
			
			HttpSession session = req.getSession();
			
			if (chk==1) {
				String passwd=req.getParameter("passwd");
				session.setAttribute("sessionpasswd",passwd);
			
				
				return "/user/updatePro.jsp";
			}
				 return  null; 
			}
		
		
		
		public String allusers(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable {
			int pageSize = 5;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String pageNum = req.getParameter("pageNum");
			if (pageNum==null || pageNum == "") {
				pageNum = "1";}
			int currentPage = Integer.parseInt(pageNum);
			int startRow = (currentPage -1) * pageSize +1;
			int endRow = currentPage * pageSize;
			int count = 0;
			int number = 0;
			List userList = null;
			UserlistDBBean dbPro = UserlistDBBean.getInstance();
			count = dbPro.getUserCount();
			if (count >0){
				userList = dbPro.getUsers(startRow,endRow);}
				number = count - (currentPage - 1)*pageSize;
			
			int bottomLine = 5;
			int pageCount =count/pageSize
					+(count % pageSize == 0 ? 0 : 1);
			int startPage = 1 + (currentPage -1) / bottomLine * bottomLine;
			int endPage = startPage + bottomLine -1;
			if (endPage > pageCount) endPage = pageCount;
			
			HttpSession session=req.getSession();
			req.setAttribute("userList",userList);
			req.setAttribute("number",number);
			req.setAttribute("count", count);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("startPage", startPage);
			req.setAttribute("bottomLine", bottomLine);
			req.setAttribute("pageCount", pageCount);
			req.setAttribute("endPage", endPage);
			req.setAttribute("sessionid", session.getAttribute("sessionid"));
			
				 return  "/user/allusers.jsp";
				 
				} 
		
		
		public String friendPage(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable {

			
			 String number = req.getParameter("number");
			 int num = Integer.parseInt(req.getParameter("num"));
			 String pageNum = req.getParameter("pageNum");

			if (pageNum == null || pageNum == "") {
					pageNum = "1";	}
			
			
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			 
			 try { 
				UserlistDBBean dbPro = UserlistDBBean.getInstance();
			 	UserlistDataBean user = dbPro.getUser(num,"content"); 
			 	SongDBBean dbPro2=SongDBBean.getInstance();
			 	
			 	HttpSession session=req.getSession();
			 	System.out.println("===========1");
			 	String sessionid=(String) session.getAttribute("sessionid");
			 	System.out.println("==========="+sessionid+":"+user.getUserid());
			 	boolean chk=dbPro.followchk(sessionid, user.getUserid());
			 	System.out.println(chk);
			 	int follow=dbPro.followCount(user.getUserid());
			 	System.out.println(follow);
			 	int follower=dbPro.followerCount(user.getUserid());
			 	System.out.println(follower);
			 	List followList=dbPro.followList(user.getUserid());
			 	List followerList=dbPro.followerList(user.getUserid());
			 	
			 	
			 	List songList=dbPro2.getPage(user.getUserid());
			 	
				 EtcinfoDBBean dao2=EtcinfoDBBean.getInstance();
				 EtcinfoDataBean etc=dao2.getEtc(user.getUserid());
				 
			 	req.setAttribute("user", user); 	
				req.setAttribute("pageNum", pageNum);
				req.setAttribute("chk", chk);
				req.setAttribute("etc", etc);
				req.setAttribute("follow", follow);
				req.setAttribute("follower", follower);
				req.setAttribute("sessionid", sessionid);
				req.setAttribute("followList", followList);
				req.setAttribute("followerList", followerList);
				req.setAttribute("songList", songList);

			 }catch(Exception e) {
				 e.printStackTrace();
			 }
			
			 return  "/view/friendPage.jsp"; 
				} 
		
		
		
		public String follow(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 
			
			String userid=req.getParameter("userid");
			String num=req.getParameter("num");
			
			HttpSession session=req.getSession();
		 	String sessionid=(String) session.getAttribute("sessionid");
		 	
			UserlistDBBean dbPro=UserlistDBBean.getInstance();
			
			boolean chkfollow=dbPro.follow(sessionid, userid);
			
			req.setAttribute("chkfollow", chkfollow);
			res.sendRedirect("friendPage?num="+num);
			
			
			return  null; 
				} 
		
		public String myPage(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable {
			

			 
			 HttpSession session = req.getSession();
			 String userid=(String) session.getAttribute("sessionid");
			 String passwd=(String) session.getAttribute("sessionpasswd");
			 
			 UserlistDBBean dao = UserlistDBBean.getInstance();
			 UserlistDataBean user=dao.getUser2(userid,passwd);
			 
			 EtcinfoDBBean dao2=EtcinfoDBBean.getInstance();
			 EtcinfoDataBean etc=dao2.getEtc(userid);
			 
			 SongDBBean dao3=SongDBBean.getInstance();
			 
			 
			 int follow=dao.followCount(userid);
			 int follower=dao.followerCount(userid);
			 List followList=dao.followList(userid);
			 List followerList=dao.followerList(userid);
			 List songList=dao3.getPage(userid);
			 
			 if(session.getAttribute("sessionid")==null)
			 {
				return "intro";
			 };
			 
			 req.setAttribute("followList", followList);
			 req.setAttribute("user", user);
			 req.setAttribute("etc", etc);
			 req.setAttribute("follow", follow);
			 req.setAttribute("follower", follower);
			 req.setAttribute("followerList", followerList);
			 req.setAttribute("songList", songList);
			 
			 	return  "/view/myPage.jsp"; 

				}
		
		public String etcInfoForm(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable {
		
			HttpSession session=req.getSession();
			String etcid=(String) session.getAttribute("sessionid");
			
			EtcinfoDBBean dao=EtcinfoDBBean.getInstance();
			
			boolean chkid=dao.chkid(etcid);
			
			
			if (chkid) {
				
				EtcinfoDataBean etc=dao.getEtc(etcid);
				req.setAttribute("etcid", etcid);
				req.setAttribute("etc", etc);
				return "/user/etcUpdateForm.jsp";
				
			}else {
				req.setAttribute("etcid", etcid);
				 return  "/user/etcInsertForm.jsp"; 
			}
			
			
				}
		
		public String etcInsertPro(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable {
			
			
			String realFolder=""; //占쏙옙 占쏙옙占시몌옙占쏙옙占싱션삼옙占쏙옙 占쏙옙占쏙옙 占쏙옙占�
			String encType="utf-8";
			int maxSize = 10*1024*1024; //占쌍댐옙 占쏙옙占싸듸옙 占쏙옙 占쏙옙占쏙옙 크占쏙옙 5Mb
			ServletContext context = req.getServletContext();
			realFolder = context.getRealPath("profileSave");
			MultipartRequest multi = null;
			multi = new MultipartRequest(req,realFolder,maxSize,encType,new DefaultFileRenamePolicy());
			Enumeration files = multi.getFileNames();
			String profilename="";
			File file = null;
			
			
			if (files.hasMoreElements()) { //占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙占� while占쏙옙 占쌕꾸몌옙 占실억옙占�!
				String name = (String) files.nextElement();
				profilename=multi.getFilesystemName(name);
				String original = multi.getOriginalFileName(name); //占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 占싱몌옙占쏙옙 占쏙옙占쏙옙 -
				String type = multi.getContentType(name);
				file = multi.getFile(name);
				
			}
			
				EtcinfoDataBean etc=new EtcinfoDataBean();
				
				
				etc.setEtcid(multi.getParameter("etcid"));
				etc.setFacelink(multi.getParameter("facelink"));
				etc.setInstalink(multi.getParameter("instalink"));
				etc.setSoundlink(multi.getParameter("soundlink"));

				
				if (file!=null) {
					etc.setProfilename(profilename);
					etc.setProfilesize((int) file.length()); 
				}
				else {
					etc.setProfilename("");
					etc.setProfilesize(0);
				}
				

				System.out.println(etc);
				EtcinfoDBBean dbPro = EtcinfoDBBean.getInstance(); 

	
				dbPro.insertEtc(etc);
			
				

				return "/user/etcInsertPro.jsp";
		}
		
		
		
		public String etcUpdatePro(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable {
			
			
			String realFolder=""; //占쏙옙 占쏙옙占시몌옙占쏙옙占싱션삼옙占쏙옙 占쏙옙占쏙옙 占쏙옙占�
			String encType="utf-8";
			int maxSize = 10*1024*1024; //占쌍댐옙 占쏙옙占싸듸옙 占쏙옙 占쏙옙占쏙옙 크占쏙옙 5Mb
			ServletContext context = req.getServletContext();
			realFolder = context.getRealPath("profileSave");
			MultipartRequest multi = null;
			multi = new MultipartRequest(req,realFolder,maxSize,encType,new DefaultFileRenamePolicy());
			Enumeration files = multi.getFileNames();
			String profilename="";
			File file = null;
			
			if (files.hasMoreElements()) { //占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙占� while占쏙옙 占쌕꾸몌옙 占실억옙占�!
				String name = (String) files.nextElement();
				profilename=multi.getFilesystemName(name);
				String original = multi.getOriginalFileName(name); //占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 占싱몌옙占쏙옙 占쏙옙占쏙옙 -
				String type = multi.getContentType(name);
				file = multi.getFile(name);
				
			}
			
			EtcinfoDataBean etc=new EtcinfoDataBean();
			
			
			etc.setEtcid(multi.getParameter("etcid"));
			etc.setFacelink(multi.getParameter("facelink"));
			etc.setInstalink(multi.getParameter("instalink"));
			etc.setSoundlink(multi.getParameter("soundlink"));


			if (file!=null) {
				etc.setProfilename(profilename);
				etc.setProfilesize((int) file.length()); 
			}
			else {
				etc.setProfilename("");
				etc.setProfilesize(0);
			}
			
			
			
			System.out.println(etc);
			EtcinfoDBBean dbPro = EtcinfoDBBean.getInstance();
			
			int chk=dbPro.updateEtc(etc); 

			req.setAttribute("etc", etc);
			
			HttpSession session = req.getSession();
			
			if (chk==1) {

				
				return "/user/etcUpdatePro.jsp";
			}
				 return  null; 
			}
		
		public String deleteImg(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable {
			
			EtcinfoDataBean etc=new EtcinfoDataBean();
			
			etc.setEtcid(req.getParameter("name"));

			String name=req.getParameter("name");
			
			System.out.println(etc);
			EtcinfoDBBean dbPro = EtcinfoDBBean.getInstance();
			int chk=dbPro.deleteImg(etc); 

			req.setAttribute("etc", etc);
			req.setAttribute("name",name);
	
				 return  "/user/NewFile.jsp";
			}
		
		public String songForm(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 
				
			int snum=0;
			 if(req.getParameter("snum")!=null){
				 snum=Integer.parseInt(req.getParameter("snum"));
			 };
			 
			req.setAttribute("snum",snum);
			
			
			return  "/song/songForm.jsp"; 
				}
		
		public String songInsert(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 
			
			
			
			String realFolder=""; 
			String encType="utf-8";
			int maxSize = 100*1024*1024; 
			ServletContext context = req.getServletContext();
			realFolder = context.getRealPath("songSave");
			MultipartRequest multi = null;
			multi = new MultipartRequest(req,realFolder,maxSize,encType,new DefaultFileRenamePolicy());
			Enumeration files = multi.getFileNames();
			String[] filename=new String[2];
			File[] file = new File[2];
			int index=0;
			
			String[] original=new String[2];
			String[] type=new String[2];

			while (files.hasMoreElements()) { 
				String name = (String) files.nextElement();
				filename[index]=multi.getFilesystemName(name);
				original[index] = multi.getOriginalFileName(name); 
				type[index] = multi.getContentType(name);
				file[index] = multi.getFile(name);
				index++;
				
			}
			

			SongDataBean song=new SongDataBean();
			
			if (req.getParameter("snum")!=null&&!req.getParameter("snum").equals("")) {
				song.setSnum(Integer.parseInt(req.getParameter("snum")));}
			
	
			song.setSboardid(multi.getParameter("sboardid"));
			song.setStitle(multi.getParameter("stitle"));
			song.setGenre(multi.getParameter("genre"));
			song.setSbio(multi.getParameter("sbio"));
			
			
			
			
			SongDBBean dbPro = SongDBBean.getInstance(); 
			
			String ctype=".jpg";
			
			int chk=0;
			
			if (filename[0]!=null) {
			ctype= filename[0].substring(filename[0].lastIndexOf(".")+1);
			
			if (!(ctype.equalsIgnoreCase("jpg")||ctype.equalsIgnoreCase("jpeg")||ctype.equalsIgnoreCase("png")||ctype.equalsIgnoreCase("gif"))) {
				chk=1;
				req.setAttribute("chk", chk);
				return  "/song/typechk.jsp";
			}
			
			
			}
			
		
			
		
			
			
			
			
			System.out.println(chk);
			String stype= filename[1].substring(filename[1].lastIndexOf(".")+1);
			if (!(stype.equalsIgnoreCase("mp3")||stype.equalsIgnoreCase("mp4")||stype.equalsIgnoreCase("wav"))) {
				chk=-1;
				req.setAttribute("chk", chk);
				return  "/song/typechk.jsp";
			}
			
	

				
			if (file[0]!=null)
			{	

				song.setCfilename(filename[0]);
				song.setCfilesize((int) file[0].length());

				
			}else {
				song.setCfilename("");
				song.setCfilesize(0);

			}
			
			song.setSfilename(filename[1]);
			song.setSfilesize((int) file[1].length()); 
			
		
			System.out.println(song);
			
			dbPro.insertSong(song);
			req.setAttribute("chk", chk);
			req.setAttribute("sboardid", song.getSboardid());
			
			return "/song/typechk.jsp";
			

		}
	
		
		
		public String songlist(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 
			
			
			int pageSize = 5;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String pageNum = req.getParameter("pageNum");
			if (pageNum==null || pageNum == "") {
				pageNum = "1";}
			int currentPage = Integer.parseInt(pageNum);
			int startRow = (currentPage -1) * pageSize +1;
			int endRow = currentPage * pageSize;
			int count = 0;
			int number = 0;
			List songList = null;
			
			
			SongDBBean dbPro = SongDBBean.getInstance();
			
			HttpSession session=req.getSession();
			
			String sboardid=req.getParameter("sboardid");
			
			if (sboardid==null||sboardid.equals("")) {
				String sessionid = (String)session.getAttribute("sessionid"); 
				sboardid=sessionid;
			}
			
			count = dbPro.getSongCount(sboardid);
			
			if (count >0){
				songList = dbPro.getSongs(startRow,endRow,sboardid);}
				number = count - (currentPage - 1)*pageSize;
			
			int bottomLine = 3;
			int pageCount =count/pageSize
					+(count % pageSize == 0 ? 0 : 1);
			int startPage = 1 + (currentPage -1) / bottomLine * bottomLine;
			int endPage = startPage + bottomLine -1;
			if (endPage > pageCount) endPage = pageCount;
			
			req.setAttribute("sboardid", sboardid);
			req.setAttribute("count", count);
			req.setAttribute("songList",songList);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("startPage", startPage);
			req.setAttribute("bottomLine", bottomLine);
			req.setAttribute("endPage", endPage);
			req.setAttribute("number",number);
			req.setAttribute("pageCount", pageCount);
			


			
				 return  "/song/songlist.jsp";
		}
		
		public String songcontent(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 

			  String number = req.getParameter("number");
			  int snum = Integer.parseInt(req.getParameter("snum"));
			  String sboardid=req.getParameter("sboardid");
			  
			   String pageNum = req.getParameter("pageNum");
				if (pageNum == null || pageNum == "") {
						pageNum = "1";	}
				
				 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
				 try { 
					SongDBBean dbPro = SongDBBean.getInstance();
				 	SongDataBean song = dbPro.getSong(snum, sboardid);
				 
				 	req.setAttribute("song", song); 	
					req.setAttribute("pageNum", pageNum);
			
				 }catch(Exception e) {
					 e.printStackTrace();
				 }
				 
				 return  "/song/songcontent.jsp"; 
				} 
		
		
		public String songUpdateForm(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable {
			
			int snum=Integer.parseInt(req.getParameter("snum"));
			
			HttpSession session = req.getSession();

			String sboardid = (String)session.getAttribute("sessionid");
	

			 try{
				 SongDBBean dbPro = SongDBBean.getInstance();
				 SongDataBean song = dbPro.getSong(snum, sboardid);

				 req.setAttribute("song", song);
				
			
				}catch (Exception e) {
					e.printStackTrace();
				}
			 return  "/song/songUpdateForm.jsp"; 
		}
		

		public String songUpdate(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable {
			
			
			String sboardid=req.getParameter("sboardid");
			
			SongDataBean song=new SongDataBean();
			
			if (req.getParameter("snum")!=null&&!req.getParameter("snum").equals("")) {
				song.setSnum(Integer.parseInt(req.getParameter("snum")));}

					
			
			song.setStitle(req.getParameter("stitle"));
			song.setGenre(req.getParameter("genre"));
			song.setSbio(req.getParameter("sbio"));
			
		
			System.out.println(song);
			SongDBBean dbPro = SongDBBean.getInstance();
			int chk=dbPro.updateSong(song); 

			req.setAttribute("song", song);
			
			HttpSession session = req.getSession();
			
				req.setAttribute("sboardid", sboardid);
				req.setAttribute("chk", chk);
				return "/song/songUpdatePro.jsp";	
	
			}
		
		public String songdeleteForm(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 
				 
			HttpSession session=req.getSession();
			String adminid=(String) session.getAttribute("sessionid");
			int snum=Integer.parseInt(req.getParameter("snum"));
			String pageNum = req.getParameter("pageNum");
			String sboardid = req.getParameter("sboardid");
		
			SongDBBean dbPro = SongDBBean.getInstance();
		 	SongDataBean song = dbPro.getSong(snum,sboardid);
			
		 	req.setAttribute("snum",snum);
		 	req.setAttribute("pageNum", pageNum);
		 	req.setAttribute("song", song);
		 	req.setAttribute("sboardid", sboardid);
		 	req.setAttribute("adminid", adminid);
		
			return  "/song/songdeleteForm.jsp"; 
				}
		
		public String songdeletePro(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 

			 String pageNum = req.getParameter("pageNum");
			 if(pageNum == null || pageNum == "") {
				 pageNum = "1";
			 }
			 
			 
			 int snum = Integer.parseInt(req.getParameter("snum"));
			 String sboardid=req.getParameter("sboardid");
			 String passwd = req.getParameter("passwd");
			 SongDBBean dbPro = SongDBBean.getInstance();

			 
			 int check = -1;
	 
			HttpSession session = req.getSession();
		 
			if (passwd.equals(session.getAttribute("sessionpasswd"))) {
			
					check=dbPro.deleteSong(sboardid,snum);
					req.setAttribute("check", check);
					return  "/song/songdeletePro.jsp"; 


			}else {
				check=-1;
			}
			
			req.setAttribute("pageNum", pageNum);
			req.setAttribute("check",check);
			req.setAttribute("sboardid", sboardid);
			
			return "/song/songdeletePro.jsp";

		}
		
		
		public String writeform_g(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 
		
	
			int gnum=0, ref=0, re_step=0,re_level=0;
			String gboardid = req.getParameter("gboardid");
			
			
			HttpSession session=req.getSession();
			if(gboardid==null) {	
				gboardid=(String) session.getAttribute("sessionid");
			}
			
			
			 
			if(req.getParameter("gnum")!=null){
			gnum=Integer.parseInt(req.getParameter("gnum"));
			ref=Integer.parseInt(req.getParameter("ref"));
			re_step=Integer.parseInt(req.getParameter("re_step"));
			re_level=Integer.parseInt(req.getParameter("re_level"));

			}

			req.setAttribute("gboardid",gboardid);
			req.setAttribute("gnum",gnum);
			req.setAttribute("ref",ref);	
			req.setAttribute("re_step",re_step);	
			req.setAttribute("re_level",re_level);
			req.setAttribute("pageNum",1);
			
			
			return "/guest/writeForm_g.jsp";
			
		}
		
		public String writePro_g(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 
		
			
			String gboardid = req.getParameter("gboardid");
			
			HttpSession session=req.getSession();
			if(gboardid==null) {	
				gboardid=(String) session.getAttribute("sessionid");
			}
			
			String pageNum=req.getParameter("pageNum");
			if (pageNum == null || pageNum == "") {
					pageNum = "1" ; }
			
			GuestDataBean msg = new GuestDataBean();
		
			if (req.getParameter("gnum")!=null&&!req.getParameter("gnum").equals("")) {
				msg.setGnum(Integer.parseInt(req.getParameter("gnum")));
				msg.setRef(Integer.parseInt(req.getParameter("ref")));
				msg.setRe_step(Integer.parseInt(req.getParameter("re_step")));
				msg.setRe_level(Integer.parseInt(req.getParameter("re_level")));};
				msg.setGboardid(req.getParameter("gboardid"));
				msg.setGtitle(req.getParameter("gtitle"));
				msg.setGcontent(req.getParameter("gcontent"));
				msg.setGemail(req.getParameter("gemail"));
				msg.setWriter(req.getParameter("writer"));
			System.out.println(msg);
			
			GuestDBBean dbPro=GuestDBBean.getInstance();
			dbPro.insertMsg(msg);
			
			req.setAttribute("pageNum", pageNum);
			res.sendRedirect(req.getContentType()+"/board/guestlist?pageNum="+pageNum+"&gboardid="+gboardid);
	
			
			return null;
		}
		
		public String guestlist(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 

			String gboardid = req.getParameter("gboardid");
			
			HttpSession session=req.getSession();
			if(gboardid==null) {	
				gboardid=(String) session.getAttribute("sessionid");
			}
			
			
			int pageSize = 5;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String pageNum = req.getParameter("pageNum");
				if (pageNum == null || pageNum == "") {
					pageNum = "1"; }
				int currentPage = Integer.parseInt(pageNum);
				int startRow = (currentPage -1) * pageSize +1;
				int endRow = currentPage * pageSize;
				int count=0;
				int number =0;
				List msgList = null;
				GuestDBBean dbPro = GuestDBBean.getInstance();
				count = dbPro.getMsgCount(gboardid);
				if (count >0) {
					msgList = dbPro.getMsgs(startRow, endRow, gboardid);}
				
				number = count - (currentPage - 1)*pageSize;
				int bottomLine = 5;
				int pageCount = count/pageSize
				+(count % pageSize == 0 ? 0 : 1);
				int startPage = 1 + (currentPage -1) / bottomLine * bottomLine;
				int endPage = startPage + bottomLine -1;
				if (endPage > pageCount) endPage = pageCount;
				
				req.setAttribute("gboardid", gboardid);
				req.setAttribute("count", count);
				req.setAttribute("msgList", msgList);
				req.setAttribute("currentPage", currentPage);
				req.setAttribute("startPage", startPage);
				req.setAttribute("bottomLine", bottomLine);
				req.setAttribute("endPage", endPage);
				req.setAttribute("number", number);
				req.setAttribute("pageCount", pageCount);
				

				return "/guest/guestlist.jsp";
		
			
		
		}
		
		public String msgcontent(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 
				String gboardid = req.getParameter("gboardid");
				
				HttpSession session=req.getSession();
				if(gboardid==null) {	
					gboardid=(String) session.getAttribute("sessionid");
				}
				
			    String number = req.getParameter("number");

			 
			 int gnum = Integer.parseInt(req.getParameter("gnum"));
			 
			 String pageNum = req.getParameter("pageNum");
				if (pageNum == null || pageNum == "") {
					pageNum = "1";	}
				
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			 try { 
				GuestDBBean dbPro = GuestDBBean.getInstance();
				GuestDataBean msg = dbPro.getMsg(gnum, gboardid,"content");
				int ref=msg.getRef(); 
				int re_step=msg.getRe_step();
				int re_level=msg.getRe_level();
				 
				req.setAttribute("msg", msg);
				req.setAttribute("pageNum", pageNum);
				
			 }catch(Exception e) {
				 e.printStackTrace();
			 };
				
				return "/guest/msgcontent.jsp";
				}
		
		public String msgdeleteForm(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 
		
			
			int gnum=Integer.parseInt(req.getParameter("gnum"));
			String pageNum = req.getParameter("pageNum");
			String gboardid=req.getParameter("gboardid");
			
			req.setAttribute("pageNum", pageNum);
			req.setAttribute("gnum", gnum);	
			req.setAttribute("gboardid", gboardid);
			
			
			return "/guest/msgdeleteForm.jsp";
		}
		
		
		public String msgdeletePro(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 
		
			String gboardid = req.getParameter("gboardid");

			HttpSession session=req.getSession();
			if(gboardid==null) {	
				gboardid=(String) session.getAttribute("sessionid");
			}
			
			String pageNum = req.getParameter("pageNum");
			if (pageNum == null || pageNum == "") {
				pageNum = "1" ; }

		
			int gnum = Integer.parseInt(req.getParameter("gnum"));
			String passwd = req.getParameter("passwd");
			String spasswd=(String) session.getAttribute("sessionpasswd");
			
			GuestDBBean dbPro = GuestDBBean.getInstance();
			
			int chk=-1;
			
			if (passwd.equals(spasswd)) {
				chk = dbPro.deleteMsg(gnum, gboardid);
				req.setAttribute("chk", chk);
				req.setAttribute("gboardid", gboardid);
				req.setAttribute("pageNum", pageNum);
				return "/guest/msgdeletePro.jsp";
			}

			req.setAttribute("chk", chk);
			
			req.setAttribute("pageNum", pageNum);
			return "/guest/msgdeletePro.jsp";
			
		}
	
		
		public String msgUpdateForm(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 
			
			String gboardid = req.getParameter("gboardid");

			HttpSession session=req.getSession();
			if(gboardid==null) {	
				gboardid=(String) session.getAttribute("sessionid");
			}
			
			
			String pageNum = req.getParameter("pageNum");
			if (pageNum == null || pageNum == "") {
				pageNum = "1" ; }
			
			
			int gnum = Integer.parseInt (req.getParameter("gnum"));
			
			try {
				GuestDBBean dbPro = GuestDBBean.getInstance();
				GuestDataBean msg = dbPro.getMsg(gnum,gboardid,"update");
				
				req.setAttribute("msg", msg);
				req.setAttribute("pageNum", pageNum);
				req.setAttribute("gboardid", gboardid);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return "/guest/msgUpdateForm.jsp";
		
		}
		
		public String msgUpdatePro(HttpServletRequest req,
				 HttpServletResponse res)  throws Throwable { 
			

			String gboardid = req.getParameter("gboardid");

			HttpSession session=req.getSession();
			if(gboardid==null) {	
				gboardid=(String) session.getAttribute("sessionid");
			}
			String pageNum = req.getParameter("pageNum");
			if (pageNum == null || pageNum == "") {
				pageNum = "1" ; }
			
			GuestDataBean msg = new GuestDataBean();
			if (req.getParameter("gnum")!=null&&!req.getParameter("gnum").equals("")) {
				msg.setGnum(Integer.parseInt(req.getParameter("gnum")));
				msg.setRef(Integer.parseInt(req.getParameter("ref")));
				msg.setRe_step(Integer.parseInt(req.getParameter("re_step")));
				msg.setRe_level(Integer.parseInt(req.getParameter("re_level")));};
				
				msg.setGboardid(req.getParameter("gboardid"));
				msg.setWriter(req.getParameter("writer"));
				msg.setGemail(req.getParameter("gemail"));
				msg.setGtitle(req.getParameter("gtitle"));
				msg.setGcontent(req.getParameter("gcontent"));

				System.out.println(msg);
				
				GuestDBBean dbPro = GuestDBBean.getInstance(); 

				int chk=dbPro.updateMsg(msg);
				req.setAttribute("pageNum", pageNum);
				req.setAttribute("chk", chk);
				req.setAttribute("gboardid", gboardid);
			
			return "/guest/msgUpdatePro.jsp";
		}
}