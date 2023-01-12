package com.cnsbd.jtrainpm.service;

import com.cnsbd.jtrainpm.dto.IUserProject;
import com.cnsbd.jtrainpm.security.UserDetailsImpl;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private UserService userService;

    @Override
    public byte[] printProjectList() {
        Long userId = UserDetailsImpl.getCurrentUserId();
        List<IUserProject> projects = userService.getProjects(userId);
        try {
            final JasperReport report = loadTemplate();
            final Map<String, Object> parameters = parameters(projects);
            final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList("projects"));
            final JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private JasperReport loadTemplate() throws JRException {
        final InputStream reportInputStream = getClass().getResourceAsStream("/reports/project-list.jrxml");
        final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);

        return JasperCompileManager.compileReport(jasperDesign);
    }

    private Map<String, Object> parameters(List<IUserProject> projects) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("UserName", UserDetailsImpl.getCurrentUser().getName());
        return parameters;
    }
}
