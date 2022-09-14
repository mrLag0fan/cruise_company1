package cruise_company.servlets.user.receipt;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import cruise_company.dao.DAOException;
import cruise_company.dao.user.receipt.ReceiptController;
import cruise_company.entity.user.User;
import cruise_company.entity.user.receipt.Receipt;
//import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class Test
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
maxFileSize = 1024 * 1024 * 5, 
maxRequestSize = 1024 * 1024 * 5 * 5)
public class DocumentsUpload extends HttpServlet{
	private static final String UPLOAD_DIRECTORY = "upload";
	private static final int MEMORY_THRESHOLD     = 1024 * 1024 * 3;  // 3MB
	private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(DocumentsUpload.class);
    public DocumentsUpload() {
        super();
    }
    
    

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("account.jsp");
	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filePath = null;
		ReceiptController rc = new ReceiptController();
		Receipt receipt = (Receipt) request.getSession().getAttribute("receipt");
		if (ServletFileUpload.isMultipartContent(request)) {

		    DiskFileItemFactory factory = new DiskFileItemFactory();
		    factory.setSizeThreshold(MEMORY_THRESHOLD);
		    factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
		    ServletFileUpload upload = new ServletFileUpload(factory);
		    upload.setFileSizeMax(MAX_FILE_SIZE);
		    upload.setSizeMax(MAX_REQUEST_SIZE);
		    String uploadPath ="D:\\Java\\EPAM_project" + File.separator + UPLOAD_DIRECTORY + File.separator + 
		    		((User)request.getSession().getAttribute("user")).getEmail();
		    File uploadDir = new File(uploadPath);
		    if (!uploadDir.exists()) {
		        uploadDir.mkdir();
		    }
		    String finalPath = uploadPath + File.separator + receipt.hashCode();
		    File finalDir = new File(finalPath);
		    if (!finalDir.exists()) {
		    	finalDir.mkdir();
		    }
		    List<FileItem> formItems = upload.parseRequest(new ServletRequestContext(request));
		    log.info(formItems);
		    if (formItems != null && formItems.size() > 0) {
		        for (FileItem item : formItems) {
			    if (!item.isFormField()) {
			        String fileName = new File(item.getName()).getName();
			        filePath = finalDir + File.separator + fileName;
		                File storeFile = new File(filePath);
		                try {
							item.write(storeFile);
						} catch (Exception e) {
							e.printStackTrace();
						}
			    	}
		        }
		    }
		}
		receipt.setDocuments(filePath);
		try {
			receipt.setReceiptStatusId(8);
			rc.create(receipt);
			request.removeAttribute("receipt");
			response.sendRedirect("account.jsp");
		}catch(DAOException ex) {
			request.setAttribute("msg", ex.getMessage());
			getServletContext().getRequestDispatcher("/error500.jsp").forward(request, response);
		}
	}

}
