import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.font.TextLayout;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class GeradoradeFigurinhas {
    public void cria(InputStream inputStream, String nomeArquivo, String pathName, String texto) throws Exception {
        // Leitura da imagem
        // InputStream inputStream = new FileInputStream(new File("entrada/filme.jpg"));
        // InputStream inputStream = new URL("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies_1.jpg").openStream();
        BufferedImage imagemOriginal = ImageIO.read(inputStream);
        
        // Criar uma nova imagem em memória
        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();
        int novaAltura = altura+200;

        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        // Copiar a imagem original para a nova imagem (em memória)
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);

        // Configurar a fonte
        var fonte = new Font("Impact", Font.BOLD, 135);
        graphics.setFont(fonte);
        graphics.setColor(Color.YELLOW);

        // Escrever uma frase na nova imagem
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D retangulo = fontMetrics.getStringBounds(texto, graphics);
        int larguraTexto = (int) retangulo.getWidth();
        int posicaoTextoX = (largura - larguraTexto) / 2;
        int posicaoTextoY = novaAltura - 50;
        graphics.drawString(texto, posicaoTextoX, posicaoTextoY);

        // Outline do texto
        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        var textLayout = new TextLayout(texto, fonte, fontRenderContext);

        Shape outline = textLayout.getOutline(null);
        AffineTransform transform = graphics.getTransform();
        transform.translate(posicaoTextoX, posicaoTextoY);
        graphics.setTransform(transform);

        var outlineStroke = new BasicStroke(largura * 0.007f);
        graphics.setStroke(outlineStroke);

        graphics.setColor(Color.black);
        graphics.draw(outline);
        graphics.setClip(outline);

        // Escrever a imagem em um arquivo
        ImageIO.write(novaImagem, "png", new File(pathName+nomeArquivo));
    }
}
