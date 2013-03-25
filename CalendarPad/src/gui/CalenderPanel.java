package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import noteProcess.noteProcess;
import calenderProcess.MyCalender;

/**
 * ��ʾ�����������
 * 
 * @author weijinshi
 * 
 */
public class CalenderPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;

	public static MyCalender cal = new MyCalender();

	private JPanel p0 = new JPanel();
	private JPanel p1 = new JPanel();
	private JPanel p2 = new JPanel();

	private TransparentButton prevYearButton = new TransparentButton("��һ��");
	private JLabel yearLabel = new JLabel("", SwingConstants.CENTER);
	private TransparentButton nextYearButton = new TransparentButton("��һ��");
	private TransparentButton prevMonthButton = new TransparentButton("��һ��");
	private JLabel monthLabel = new JLabel("", SwingConstants.CENTER);
	private TransparentButton nextMonthButton = new TransparentButton("��һ��");

	private TransparentButton[] dayButtons = new TransparentButton[42];

	public CalenderPanel()
	{
		prevYearButton.setBounds(20, 0, 50, 30);
		yearLabel.setBounds(80, 0, 40, 30);
		nextYearButton.setBounds(130, 0, 50, 30);
		prevMonthButton.setBounds(240, 0, 50, 30);
		monthLabel.setBounds(300, 0, 40, 30);
		nextMonthButton.setBounds(350, 0, 50, 30);

		prevYearButton.addActionListener(this);
		nextYearButton.addActionListener(this);
		prevMonthButton.addActionListener(this);
		nextMonthButton.addActionListener(this);

		p0.setOpaque(false);
		p0.setBounds(40, 60, 500, 50);
		p0.setLayout(null);
		p0.add(prevYearButton);
		p0.add(yearLabel);
		p0.add(nextYearButton);
		p0.add(prevMonthButton);
		p0.add(monthLabel);
		p0.add(nextMonthButton);

		p1.setOpaque(false);
		p1.setBounds(0, 80, 500, 80);
		p1.setLayout(new GridLayout(1, 7));
		p1.add(new JLabel("����", SwingConstants.CENTER));
		p1.add(new JLabel("��һ", SwingConstants.CENTER));
		p1.add(new JLabel("�ܶ�", SwingConstants.CENTER));
		p1.add(new JLabel("����", SwingConstants.CENTER));
		p1.add(new JLabel("����", SwingConstants.CENTER));
		p1.add(new JLabel("����", SwingConstants.CENTER));
		p1.add(new JLabel("����", SwingConstants.CENTER));

		cal.refreshDaysArray();

		p2.setOpaque(false);
		p2.setBounds(0, 130, 500, 420);
		p2.setLayout(new GridLayout(6, 7));
		refresh();

		this.setOpaque(false);
		this.setLayout(null);
		this.add(p0);
		this.add(p1);
		this.add(p2);
	}

	/**
	 * �����ҳ�� ˢ������������ʾ
	 */
	public void refresh()
	{
		p2.removeAll();
//		p2.invalidate() ;

		for (int i = 0; i < 42; i++)
		{
			dayButtons[i] = new TransparentButton(i);

			// ���������ʾ����
			dayButtons[i].addActionListener(new noteProcess());

			String dayTitle = cal.daysArray[i].getDayTitle();

			dayButtons[i].setText(cal.getDaysArray()[i].getDayText());

			if (MainFrame.isLogin)
			{
				// ���б��������
				if (cal.daysArray[i].getHasReminder())
				{
					dayButtons[i].setOpaque(true);
					dayButtons[i].setBackground(Color.yellow);
				}
			}

			// ���ǽ���ʱ�������
			if (dayTitle.length() == 8
					&& dayTitle.substring(0, 4).equals(MyCalender.curyear + "")
					&& dayTitle.substring(4, 6).equals(
							(MyCalender.curmonth < 10) ? "0" + MyCalender.curmonth : MyCalender.curmonth + "")
					&& dayTitle.substring(6, 8).equals(
							(MyCalender.curday < 10) ? "0" + MyCalender.curday : MyCalender.curday + ""))
			{
				dayButtons[i].setOpaque(true);
				dayButtons[i].setBackground(Color.green);
			}

			p2.add(dayButtons[i]);

		}

		yearLabel.setText(MyCalender.year + "");
		monthLabel.setText(MyCalender.month + "");

		p2.validate();

	}

	/**
	 * �¼����������� ��Ҫ���������ķ�ҳ�¼�
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == prevYearButton)
		{
			MyCalender.year--;
		}
		else if (e.getSource() == nextYearButton)
		{
			MyCalender.year++;
		}
		else if (e.getSource() == prevMonthButton)
		{
			MyCalender.month--;
			if (MyCalender.month < 1)
			{
				MyCalender.month = 12;
				MyCalender.year--;
			}
		}
		else if (e.getSource() == nextMonthButton)
		{
			MyCalender.month++;
			if (MyCalender.month > 12)
			{
				MyCalender.month = 1;
				MyCalender.year++;
			}
		}

		cal.refreshDaysArray();
		refresh();
	}

}