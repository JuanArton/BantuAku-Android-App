package com.TadpoleStudio.BantuAku;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.loader.content.CursorLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class JavaMailAPI extends AsyncTask<Void, Void, Void> {

    private Context context;

    private Session session;
    private String email, subject, message, path, attachFile;

    public JavaMailAPI(Context context, String email, String subject, String message, String path) {
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.path = path;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Utils.EMAIL, Utils.PASSWORD);
            }
        });

        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(Utils.EMAIL));
            msg.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(email)));
            msg.setSubject(subject);

            //sett the message
            MimeBodyPart pesan = new MimeBodyPart();
            pesan.setText(message);

            //preparing for the attachment part
            Multipart multipart = new MimeMultipart();
            MimeBodyPart attachPart = new MimeBodyPart();

            Uri uri = Uri.parse(path); //parsing the path
            attachFile = tempFileGenerator(context, uri); //generating temp image file to be uploaded

            //renaming the temp image file to jpeg
            File from = new File(attachFile);
            File to = new File(attachFile+".jpeg");
            from.renameTo(to);

            //attaching the temp image
            Log.d("ADebugTag", "Value: " + attachFile);
            DataSource source = new FileDataSource(attachFile+".jpeg");
            attachPart.setDataHandler(new DataHandler(source));
            attachPart.setFileName(new File(attachFile+".jpeg").getName());

            //input the message and attachment to multipart
            multipart.addBodyPart(pesan);
            multipart.addBodyPart(attachPart);
            msg.setContent(multipart);

            Transport.send(msg); //send message
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String tempFileGenerator(
            @NonNull Context context, @NonNull Uri uri) {
        final ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver == null)
            return null;

        String filePath = context.getApplicationInfo().dataDir + File.separator
                + System.currentTimeMillis();

        File file = new File(filePath);
        try {
            InputStream inputStream = contentResolver.openInputStream(uri);
            if (inputStream == null)
                return null;

            OutputStream outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0)
                outputStream.write(buf, 0, len);

            outputStream.close();
            inputStream.close();
        } catch (IOException ignore) {
            return null;
        }

        return file.getAbsolutePath();
    }

}
